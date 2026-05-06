package com.xxd.kt.coroutines.flow

import com.xxd.kt.coroutines.utils.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

/**
 *    author : xxd
 *    date   : 2026/4/7
 *    desc   : 第一次接触Flow,探索基本用法
 *             作用： suspending function 可以异步返回一个值。但是需要返回多个值的情况下，就必须用到 Flows
 */
class Basic {

    // 储存多个value，可以使用collections， 使用它们可以 forEach
    fun m1() {
        val list: List<Int> = listOf(1, 2, 3)
        list.forEach {
            log(it)
        }
    }

    // Sequence 可以流式处理数据（同步冷流），可以做在处理好一个数据后就发出(会阻塞主线程),不用等待所有数据一起处理完毕
    fun m2() {
        val sequence: Sequence<Int> = sequence {
            log("执行 sequence") // 冷流，必须收集才开始执行block代码块
            for (i in 1..3) {
                Thread.sleep(100) // 模拟cpu计算耗时
                yield(i)
            }
        }
        log(":start")  // 优先于sequence的block代码块执行
        sequence.forEach { log(it) }
        log(":again")
        sequence.forEach { log(it) }  // 冷流可以重复收集
        log(":end")
    }

    // suspend 方法可以不阻塞主线程，但是只能一次返回所有数据
    fun m3() {
        suspend fun simple(): List<Int> {
            delay(1000)
            return listOf(1, 2, 3)
        }

        runBlocking {  // 这里阻塞线程只是为了执行完毕
            // 1. 进行了EvenLoop调度，放入队列
            launch {
                simple().forEach { log(it) }
            }
            // 2. 普通代码
            log("收集冷流")
        }
    }

    // flow 登场，可以实现2个功能。 1：流式处理数据，处理好一个数据就发出； 2：不会阻塞主线程
    fun m4() {
        fun simple(): Flow<Int> = flow {
            for (i in 1..3) {
                delay(100)
                emit(i)
            }
        }

        runBlocking {
            // 开启一个并发的协程，检测主线程是否被阻塞
            launch {
                for (i in 1..3) {
                    log("不阻塞主线程: $i ")
                    delay(100)
                }
            }

            simple().collect { log(it) }
        }
    }

    // flow 特点1： 冷流，可以多次收集
    fun m5() {
        val flow = flow {
            for (i in 1..3) {
                delay(100)
                emit(i)
            }
        }

        runBlocking {
            flow.collect { log("第一次收集: $it") }
            flow.collect { log("第二次收集: $it") }
        }
    }

    // flow 特点2：可以被取消
    fun m6() {
        val flow = flow {
            for (i in 1..3) {
                delay(100)
                emit(i)
            }
        }

        runBlocking {
            /*
            withTimeout 之类的方法都是创建了一个子Job挂载到父Job上，到定时后调用job.cancel,并抛出一个TimeoutCancellationException。
            withTimeoutOrNull会捕获该异常，然后返回null
            */
            withTimeoutOrNull(250) {
                flow.collect { log(it) }
            }
        }
    }

    // flow 特点3：有多种创建方式
    fun m7() {
        runBlocking {
            (1..3).asFlow()
                .collect { log(it) }
        }
    }

    // flow中间操作符有很多： 转化操作符 ：map
    fun m8() {
        runBlocking {
            (1..3).asFlow()
                .map {
                    delay(100)
                    it * it
                }
                .collect {
                    log(it)
                }
        }
    }

    // flow中间操作符有很多： 转化操作符 ：transform 更加基础
    fun m9() {
        runBlocking {
            (1..3).asFlow()
                .transform {
                    emit(it)
                    emit("复制emit: $it")
                }
                .collect {
                    log(it)
                }
        }
    }

    // flow中间操作符有很多： 数量限制操作符 ：take 内部抛出异常终止flow，然后捕获该异常内部消化
    fun m10() {
        runBlocking {
            (1..10).asFlow()
                .map { delay(100) }
                .take(2)
                .collect {
                    log(it)
                }
        }
    }

    // flow 终端操作符不止有collect，还有很多，全都是suspend方法，如toList,toSet,first,reduce,fold
    fun m11() {
        runBlocking {
            val reduce = (1..10).asFlow()
                .reduce { accumulator, value ->
                    accumulator + value
                }
            log(reduce)
        }
    }
}

fun main() {
    val basic = Basic()

    basic.m5()

}