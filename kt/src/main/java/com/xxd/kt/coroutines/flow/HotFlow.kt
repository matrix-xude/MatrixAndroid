package com.xxd.kt.coroutines.flow

import com.xxd.kt.coroutines.utils.log
import com.xxd.kt.coroutines.utils.logAndTime
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

/**
 *    author : xxd
 *    date   : 2026/5/6
 *    desc   : hot flow 区分与 cold flow, 主要是热流永远不会因为subscriber的操作而结束，一般只与开启热流的scope生命周期绑定。
 *              这是一种类似于广播的机制，所有subscriber随意监听、离开。
 *              接口为SharedFlow，具体实现类为 SharedFlowImpl
 */
class HotFlow {

    // 创建一个标准的热流，可以有多个订阅者
    fun m1() {
        /*
            replay : 重播次数，subscriber首次监听后拿到的缓存数据
            extraBufferCapacity : 缓存容器大小，这是影响的是subscriber消费数据过慢，而新数据到过快的情景。在容器满之前，不需要等待消费者
            onBufferOverflow : 缓存策略，一共有3种。都是生产者速度大于消费者才用到
         */
        val sharedFlow =
            MutableSharedFlow<Int>(replay = 3, extraBufferCapacity = 5, onBufferOverflow = BufferOverflow.SUSPEND)

        runBlocking {
            launch {  // 热流生成数据，1000ms一个，当replay + capacity 满时，就会挂起
                var i = 0
                while (true) {
                    delay(1000)
                    log("SharedFlow生成了数据: $i")
                    sharedFlow.emit(i++)
                }
            }

            launch { // 订阅者1 ： 消费热流的数据 1500ms 一次，典型的背压问题，最终导致SharedFlow的emit方法挂起
                sharedFlow.collect {
                    delay(1500)
                    log("订阅者1: 处理了数据( $it )")
                }
            }

            launch {  // 订阅者2 ：消费热流的数据 500ms 一次，因为快于SharedFlow生成速度，所以理论上最终收到数据速度为1000ms一次，但是SharedFlow受到订阅者1拖累，会低于1000ms一次
                sharedFlow.collect {
                    delay(500)
                    log("订阅者2: 处理了数据( $it )")
                }
            }
        }
    }

    // SharedFlow 接口中，collect方法返回值是 Nothing，表示为永远无法正常结束
    fun m2() {
        val sharedFlow = MutableSharedFlow<Int>()

        runBlocking {
            launch {
                var i = 0
                while (true) {
                    delay(1000)
                    sharedFlow.emit(i++)
                }
            }

            launch {  // 错误示例，不要热流的collect 后执行任何代码，如果要多次collect,需要使用多个 launch
                sharedFlow.collect {
                    log(it)
                }
                log("永远不会打印，因为collect返回值是Nothing,永远不会正常结束")
            }
        }
    }

    // StateFlow 是一种特殊的SharedFlow,(replay=1，extraBufferCapacity=0,onBufferOverflow=DROP_OLDEST)，但是实现并不是传入这些参数，而是优化后实现了一遍，原理不变
    fun m3() {
        val stateFlow = MutableStateFlow(0)

        runBlocking {
            launch {
                for (i in 1..10) {
                    delay(100)
                    // update是普通方法，而不是suspend，因为 onBufferOverflow=DROP_OLDEST，不存在挂起，所以不需要suspend方法
                    stateFlow.update {  // update 接收的参数为 function: (T) -> T ， 而不是一个简单的 T， 是因为这是 CAS 操作，保证原子性，必须需要旧值
                        i
                    }
                }
            }

            launch {  // 订阅者1，接收速度低于生产速度，所以会漏掉一些数据，只拿到最新值
                stateFlow.collect {
                    delay(150)
                    log("订阅者1： 接收数据（ $it ）")
                }
            }

            launch {  // 订阅者2，每次都拿到最新值，不会被订阅者1拖累。因为 onBufferOverflow=DROP_OLDEST ，不存在挂起
                stateFlow.collect {
                    log("订阅者2： 接收数据（ $it ）")
                }
            }
        }
    }

    // 把 cold flow 转为 hot flow ,这样flow的block代码块只会执行一次（无论有多少个订阅者）
    // 生成的是 ReadonlySharedFlow,不能在手动调用emit,这是设计原则是“单一生产者原则“
    fun m4() {
        val flow = flow {
            for (i in 1..3) {
                delay(200)
                logAndTime("冷流发出数据：$i")
                emit(i)
            }
        }

        runBlocking {
            /*
                scope: 冷流转热流，必须要 CoroutineScope，因为需要collect冷流。（内部就是 scope.launchSharing,开启了一个新协程）
                started ：3种开启新协程方式。
                    1.Eagerly：立马开启，永不结束；
                    2：Eagerly：第一个订阅者来了再开启，永不结束；
                    3：WhileSubscribed()，有订阅来了开启，所有订阅者没有了关闭。再次有新订阅者又会开启
                replay ： 重播次数（注意，设置为0时，并且配置SharingStarted.Eagerly，会立马收集冷流，不管有没有订阅者）
             */
            val sharedFlow = flow.shareIn(scope = this, started = SharingStarted.Eagerly, replay = 3)

            launch {
                logAndTime("订阅者1 开始收集数据~~~~")
                sharedFlow.collect {
                    delay(100)
                    logAndTime("订阅者1 收集到数据: $it")
                }
            }

            launch {
                logAndTime($"订阅者2 开始收集数据~~~~")
                sharedFlow.collect {
                    delay(50)
                    logAndTime("订阅者2 收集到数据: $it")
                }
            }
        }
    }

    // 使用 shareIn 配置  WhileSubscribed()策略，只有订阅者来时才启动（在获取数据库数据、定位信息 等业务中常用，只有有订阅者才需要执行）
    fun m5() {
        val flow = flow {
            for (i in 1..3) {
                delay(500) // 每500ms 生产一个数据
                logAndTime("冷流发出数据：$i")
                emit(i)
            }
        }

        runBlocking {
            // 使用 WhileSubscribed()策略，只有订阅者来才启动sharedFlow
            val sharedFlow = flow.shareIn(scope = this, started = SharingStarted.WhileSubscribed(), replay = 0)

            val job1 = launch {
                logAndTime("订阅者1 开始收集数据~~~~")
                sharedFlow.collect {
                    logAndTime("订阅者1 收集到数据: $it")
                }
            }

            launch { // 订阅者1 600ms后取消，这时sharedFlow只够生产一个数据，第二数据生产到一半就取消了，也就是delay执行中途被cancel
                delay(600)
                job1.cancel()
            }


            val job2 = launch {
                delay(2000)  // 订阅者2，先休息2秒，再开始收集。这时生产者 sharedFlow 会再次开启
                logAndTime("订阅者2 开始收集数据~~~~")
                sharedFlow.collect {
                    logAndTime("订阅者2 收集到数据: $it")
                }
            }
        }
    }

    // shareIn 生成的热流，与参数 scope 强绑定（内部就是scope.launch），当前scope被取消，热流也被取消
    fun m6() {
        val flow = flow {
            for (i in 1..3) {
                delay(500) // 每500ms 生产一个数据
                logAndTime("冷流发出数据：$i")
                emit(i)
            }
        }

        runBlocking {
            // 使用自定义的空间创建sharedFlow
            val coroutineScope = CoroutineScope(Job() + CoroutineName("我的协程"))
            val sharedFlow = flow.shareIn(scope = coroutineScope, started = SharingStarted.Lazily, replay = 0)

            launch {  // 订阅者感知不到 sharedFlow 取消，会一直等待
                logAndTime("订阅者1 开始收集数据~~~~")
                sharedFlow.collect {
                    logAndTime("订阅者1 收集到数据: $it")
                }
            }

            launch {  // 700 ms 后取消 scope, sharedFlow随之不再发出数据
                delay(700)
                coroutineScope.coroutineContext.job.cancel()
            }
        }
    }

    // sharedIn 如何像 MutableStateFlow一样配置参数，使用 buffer 即可
    fun m7() {
        val flow = flow {
            for (i in 1..10) {
                logAndTime("冷流发出数据：$i")
                emit(i)
            }
        }.buffer(capacity = 3, onBufferOverflow = BufferOverflow.SUSPEND) // 类似FusibleFlow，在创建MutableSharedFlow时会使用该参数

        runBlocking {
            // 注意，buffer 后紧跟 sharedIn 才生效
            val sharedFlow = flow.shareIn(scope = this, started = SharingStarted.Lazily, replay = 0)

            launch {
                logAndTime("订阅者1 开始收集数据~~~~")
                sharedFlow.collect {
                    delay(1000) // 消费者速度小于生产者，但是因为配置了buffer (capacity = 3),所以前3个生产不会挂起
                    logAndTime("订阅者1 收集到数据: $it")
                }
            }
        }
    }

    // stateIn 类似 sharedIn， 类似 replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST，使用buffer不匹配的参数会失效
    fun m8() {
        val flow = flow {
            for (i in 1..10) {
                delay(10)
                logAndTime("冷流发出数据：$i")
                emit(i)
            }
        }

        runBlocking {
            // 这种 sateIn 只接收一个参数，必须等待 flow emit 出一个value,才会执行后面的代码，所以内部是挂起的
            val sharedFlow = flow.stateIn(scope = this)
            log(": start") // 后于 emit 1 执行

            launch {
                sharedFlow.collect {
                    delay(1000) // 消费者速度小于生产者，但是因为配置了buffer (capacity = 3),所以前3个生产不会挂起
                    logAndTime("收集到数据: $it") // 收集到 1 、 10
                }
            }
        }
    }

    // 与 m8 相比，只是 stateIn 的方法参数不同
    fun m9() {
        val flow = flow {
            for (i in 1..10) {
                delay(10)
                logAndTime("冷流发出数据：$i")
                emit(i)
            }
        }

        runBlocking {
            // 这种 sateIn 接收3个参数，同时需要一个initialValue
            val sharedFlow = flow.stateIn(scope = this, started = SharingStarted.Eagerly, initialValue = -1)
            log(": start") // 先于 emit 1 执行

            launch {
                sharedFlow.collect {
                    delay(1000) // 消费者速度小于生产者，但是因为配置了buffer (capacity = 3),所以前3个生产不会挂起
                    logAndTime("收集到数据: $it")  // 收集到 -1 、 10
                }
            }
        }
    }

    // callBackFlow 可以把回调转为 Flow，因为必须调用 suspend方法 awaitClose，所以该冷流也可以一直运行，直到collect的Job被取消
    fun m10() {
        // 原理是通过创建带有 Channel 的 cold Flow
        val callbackFlow = callbackFlow {  // 这里的this为 ProducerScope , 同时也是 SendChannel
            // 1. 创建回调对象
            val listener = object : CityManager.CityNameListener {
                override fun onCityGet(cityName: String) {
                    // 2. 将数据发送给流的收集者
                    trySend(cityName)
                }
            }

            // 3. 注册回调
            CityManager.registerListener(listener)

            // 4. 关键：挂起协程，直到流被取消（比如收集者的 Job 被 cancel）
            awaitClose {
                // 5. 在流关闭时注销监听器，防止内存泄漏
                CityManager.unRegisterListener(listener)
            }
        }

        runBlocking {
            val job = launch {
                callbackFlow.collect {
                    log(it)
                }
            }

            launch {
                delay(8000) // 8秒后取消Job
                job.cancel()
            }
        }
    }

    // callBackFlow 配合 sharedIn ，可以使得callBack注册监听只执行一次，同时给多个订阅者提供数据（适合定位等场景）
    fun m11() {
        val callbackFlow = callbackFlow {
            // 1. 创建回调对象
            val listener = object : CityManager.CityNameListener {
                override fun onCityGet(cityName: String) {
                    // 2. 将数据发送给流的收集者
                    trySend(cityName)
                }
            }

            // 3. 注册回调
            CityManager.registerListener(listener)

            // 4. 关键：挂起协程，直到流被取消（比如收集者的 Job 被 cancel）
            awaitClose {
                // 5. 在流关闭时注销监听器，防止内存泄漏（在转为热流后，依然有用，在scope取消后 or WhileSubscribed()模式下，订阅者从 1 -> 0 也会执行）
                CityManager.unRegisterListener(listener)
            }
        }.buffer(capacity = 2, onBufferOverflow = BufferOverflow.DROP_OLDEST)  // 配置buffer，转sharedFlow前可以增加策略


        runBlocking {
            // 转为 sharedFlow后，CityManager.registerListener(listener)只会执行一次了
            val sharedFlow = callbackFlow.shareIn(this, SharingStarted.Lazily)

            launch {
                sharedFlow.collect {
                    log("订阅者1 collect: $it")
                }
            }

            launch {
                delay(2000)
                sharedFlow.collect {
                    log("订阅者2 collect: $it")
                }
            }

            launch {
                delay(5000)
                sharedFlow.collect {
                    log("订阅者3 collect: $it")
                }
            }

        }
    }

    object CityManager {
        val cityNames = arrayOf("北京", "南京", "San Francisco", "布达佩斯", "宾特朱贝勒", "浣熊市")

        var thread: Thread? = null

        fun registerListener(listener: CityNameListener) {
            log("start 监听城市")
            thread = Thread {
                while (true) {
                    val i = Random.nextInt(0, cityNames.size)
                    try {
                        Thread.sleep(1000)
                    } catch (e: Exception) {
                        break
                    }
                    listener.onCityGet(cityNames[i])
                }
            }
            thread?.start()
        }

        fun unRegisterListener(listener: CityNameListener) {
            log("end 监听城市")
            thread?.interrupt()
        }

        interface CityNameListener {
            fun onCityGet(cityName: String)
        }
    }

}

fun main() {
    val hotFlow = HotFlow()
    hotFlow.m11()
}