package com.xxd.kt.coroutines.flow

import com.xxd.kt.coroutines.utils.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

/**
 *    author : xxd
 *    date   : 2026/4/15
 *    desc   : Flow 必须保持上下文在同一环境中执行
 */
class FlowContext {

    // 一个简单的Flow，上下文是确定的，emit执行在调用collect的协程环境中
    fun m1() {
        val flow = flow {
            log("Started simple flow")
            for (i in 1..3) {
                emit(i)
            }
        }

        // 直接看该代码即可，不需要关心flow的实现细节，就可以确定 log(it) 发生在 Main 线程中
        runBlocking {
            flow.collect { log(it) }
        }

        // 直接看该代码即可，不需要关心flow的实现细节，就可以确定 log(it) 发生在 Dispatchers.Default 线程中
        runBlocking(Dispatchers.Default) {
            flow.collect { log(it) }
        }
    }

    // 一个Flow中，如果 emit 与 collect 时上下文不一致，则会抛出异常
    fun m2() {
        val flow = flow {
            log("Started pitfall flow")
            for (i in 1..3) {
                // 切换上下文
                coroutineScope {
                    emit(i)
                }
            }
        }

        // collect 发生在Main线程, 而emit发生在Dispatchers.IO线程，所以会抛出异常
        runBlocking {
            flow.collect { log(it) }
        }

        /*
        具体异常信息如下:
        java.lang.IllegalStateException: Flow invariant is violated:
		Flow was collected in [BlockingCoroutine{Active}@28a1f541, BlockingEventLoop@5da5dc60],
		but emission happened in [DispatchedCoroutine{Active}@56f7cac2, Dispatchers.IO].
         */
    }

    // 使用 flowOn 改变上下文
    fun m3() {
        val flow = flow {
            for (i in 1..3) {
                log("emit $i")
                // emit在Dispatchers.IO线程
                emit(i)
            }
        }.flowOn(Dispatchers.IO)

        // collect 在Main线程
        runBlocking {
            val measureTime = measureTimeMillis {
                flow.collect { log("collect $it") }
            }
            log("measureTime: $measureTime")
        }

        /*
        这里打印结果不确定，并且emit 1, collect 1 这样交替打印，因为 flowOn创建了新的协程，不与collect所在的协程环境保持一致
        能确定的就是collect一定在Main线程执行，而emit在Dispatchers.IO线程执行
         */
    }

    // Buffer 可以把打破 emit和collect 顺序执行，处理 emit 耗时，colletor 也耗时的场景
    fun simpleFlow(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100) // pretend we are asynchronously waiting 100 ms
            log("emit $i")
            emit(i)
        }
    }

    // 不使用 buffer
    fun m4() {
        runBlocking {
            val measureTime = measureTimeMillis {
                simpleFlow()
                    .collect {
                        delay(300) // pretend we are processing it for 300 ms
                        log(it)
                    }
            }
            // 打印时间为 1200ms多，按照顺序执行
            log("measureTime: $measureTime")
        }
    }

    // 使用 buffer ，原理是在指定的Channel缓存数据 , 在单独的协程collect
    fun m5() {
        runBlocking {
            val measureTime = measureTimeMillis {
                simpleFlow()
                    .buffer() // buffer 把上下游分为了2个协程
                    .collect {
                        delay(300) // pretend we are processing it for 300 ms
                        log(it)
                    }
            }
            // 打印时间为 1000ms多，因为第一次collect需要等待 emit 的100ms, 自身处理是300ms * 3
            log("measureTime: $measureTime")
        }
    }

    // Conflation 可以只保留emit最新value,在collect耗时比较久的场景使用，比如时时定位，处理定位数据后，只需要拿到最新的value即可，其他value可以丢弃
    fun m6() {
        runBlocking {
            val measureTime = measureTimeMillis {
                simpleFlow()
                    .conflate() // conflate emissions, don't process each one
                    .collect { value ->
                        delay(300) // pretend we are processing it for 300 ms
                        println(value)
                    }
            }
            // 打印 700ms多, 等待第一次emit 100ms， 自身处理一次数据 300ms，这时emit已经发送了所有value,只需要拿到最新value处理即可，再耗时300ms
            log("Collected in $measureTime ms")
        }
    }

    // 在处理collect耗时比较久的场景中，除了conflation。也可以使用 xxxLatest 操作符，它的原理是在新的value到达后，如果collect没执行完，就取消colletor的block代码块
    fun m7() {
        runBlocking {
            val measureTime = measureTimeMillis {
                simpleFlow()
                    .collectLatest { value ->
                        log("Collecting $value")
                        delay(300) // pretend we are processing it for 300 ms
                        log("Done $value")
                    }
            }
            /* 打印 600ms多,
                第一次emit 100ms，处理中；
                第二次emit 100ms，这时第一次collect没有处理完毕，取消该block代码块，执行第二次的collect
                第三次emit 100ms，这时第二次collect没有处理完毕，取消该block代码块，执行第三次的collect
                第三次collect 300ms，处理完毕
                *
             */
            log("Collected in $measureTime ms")
        }
    }

    // buffer,flowOn具体是怎么实现的，用到了channel, 把一个独立协程分为 2个独立协程，使用channel发送，接收数据
    @OptIn(ExperimentalCoroutinesApi::class)
    fun m8() {
        runBlocking {
            log(":start")
            // produce 会产生一个独立的协程,这样simpleFlow().collect 就发生在自己的协程中了
            val receiveChannel = produce(capacity = 3) {
                simpleFlow()
                    // send 方法会把 数据发送到 channel中
                    .collect { send(it) }
            }
            // 只要拿到了channel管道，可以在任意协程中接收数据，这样produce,consume 就分开在不同协程中执行了
//            receiveChannel.consumeEach {  // 这一行无法反编译，实现用这个API比较好，会有异常传播
            for (it in receiveChannel) {
                delay(300)
                log(it)
            }
        }
    }

    // 不用 produce , 手动创建 Channel
    @OptIn(ExperimentalCoroutinesApi::class)
    fun m9() {
        runBlocking {
            val channel = Channel<Int>(capacity = 3)

            // 发送在独立协程中
            val job = launch {
                simpleFlow()
                    .collect {
                        channel.send(it)
                    }
            }
            // 必须手动调用 channel.close(cause) , 否则接收的协程会一直等待
            // produce生成的AbstractCoroutine在onComplete中调用了channel.close()，所以不需要手动关闭
            job.invokeOnCompletion { cause: Throwable? ->
                channel.close(cause)
            }

            // 接收在独立协程中，与发送协程没有父子Job关联
            launch {
                for (item in channel) {
                    delay(300)
                    log(item)
                }
            }
        }
    }

    // 组合多个Flow 的操作符，如 zip ,组合2个Flow中少的数量
    fun m10() {
        val ints = (1..5).asFlow()
        val strs = flowOf("高端", "大气", "上档次")
        val flow = ints.zip(strs) { i, str ->
            "$i , $str"
        }

        runBlocking {
            flow.collect {
                log(it)
            }
        }
    }

    // 组合多个Flow 的操作符，在加上时间限制，更能体现不同的作用。
    fun m11() {
        val ints = (1..5).asFlow().onEach { delay(300) }
        val strs = flowOf("高端", "大气", "上档次").onEach { delay(400) }

        // zip 操作符内部使用 channel中 capacity = 0；必须等待2个flow的数据都获取到新value在emit; 只要有一个flow结束，立马cancel channel
        val flowZip = ints.zip(strs) { i, str ->
            "zip: $i , $str"
        }

        // combine 操作符也是用了 channel, 不过capacity = 2, 并且使用了"纪元计数" 与 "同源中断", 每次都会拿出一批缓存数据emit,并且可能丢弃一部分数据
        val flowCombine = ints.combine(strs) { i, str ->
            "combine: $i , $str"
        }

        runBlocking {
            flowZip.collect {
                log(it)
            }

            flowCombine.collect {
                log(it)
            }
        }
    }

    // flot 操作符，就是为了把Flow<Flow<T>> 转换成 Flow<T>，所以叫做平铺操作符
    // 一个把 value 转换成 Flow<T> 的的数据源
    fun requestFlow(i: Int): Flow<String> = flow {
        emit("$i: First")
        delay(500) // 模拟挂起
        emit("$i: Second")
    }

    // flatMapConcat, 顺序平铺。即先把1平铺，执行完后；再接收2平铺；以此类推
    @OptIn(ExperimentalCoroutinesApi::class)
    fun m12() {
        val flow = (1..3).asFlow()
            .onEach { delay(100) }
            .flatMapConcat { requestFlow(it) }

        runBlocking {
            flow.collect {
                log(it)
            }
        }
    }

    // flatMapMerge, 并行平铺。即接收1平铺；接收2后也平铺，不等1执行完毕；以此类推
    @OptIn(ExperimentalCoroutinesApi::class)
    fun m13() {
        val flow = (1..3).asFlow()
            .onEach { delay(100) }
            .flatMapMerge { requestFlow(it) }

        runBlocking{
            flow.collect {
                log(it)
            }
        }
    }

    // flatMapLatest, 与 xxxLatest操作符类似；都是在新的value到达后，如果collect没执行完，就取消colletor的block代码块
    @OptIn(ExperimentalCoroutinesApi::class)
    fun m14() {
        val flow = (1..3).asFlow()
            .onEach { delay(100) }
            .flatMapLatest { requestFlow(it) }

        runBlocking{
            flow.collect {
                log(it)
            }
        }
    }

}


fun main() {
    val flowContext = FlowContext()

    flowContext.m14()
//    flowContext.m5()
}