package com.xxd.kt.coroutines.suspend

import com.xxd.kt.coroutines.context.MyInterceptor
import com.xxd.kt.coroutines.context.NoDefaultInterceptor
import com.xxd.kt.coroutines.utils.log
import com.xxd.kt.gson.parse.m2
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume

/**
 *    author : xxd
 *    date   : 2026/4/7
 *    desc   : runBlocking 的调度依赖内内部实现的 EventLoop 调度器。
 *              如果传入其它调度器，runBlocking会阻塞，就是调用 parkNanos（Long.MAX_VALUE）,在内部的JobSupport中 _state处于Complete在调用unpark
 */

class RunBlockSeek {
    fun m1() {
        runBlocking {  // runBlocking内部实现了一个 EventLoop 调度器，所以打印为 3 1 2
            launch {
                log("launch进入") // 1
                delay(1000)
                log("子任务完成") // 2
            }
            log("主程序运行中...") // 3
        }
    }

    fun m2() {
        runBlocking {  // NoDefaultInterceptor 替代 runBlocking内部实现的 EventLoop 调度器，所以打印为 1 3 2
            launch(NoDefaultInterceptor()) {
                log("launch进入") // 1
                delay(1000)
                log("子任务完成") // 2
            }
            log("主程序运行中...") // 3
        }
    }

    fun m3() {
        runBlocking {  // 与 m1 相比，多了一个yield()，重新放入队列，所以执行为 1 3 2
            launch {
                log("launch进入") // 1
                delay(1000)
                log("子任务完成") // 2
            }
            yield() // 利用调度器包装，EventLoop会把后面的代码打包重新排队
            log("主程序运行中...") // 3
        }
    }

    fun m4() {
        runBlocking {  // 与 m2 相比，delay换成了 mySuspend()，所以执行为 1 3 …… , 并且永不结束，因为launch开启的Job永远没法完成
            launch(NoDefaultInterceptor()) {
                log("launch进入") // 1
                mySuspend()
                log("子任务完成") // 2
            }
            log("主程序运行中...") // 3
        }
    }

    // 挂起，并且永不resume
    suspend fun mySuspend(): Int {
        return suspendCoroutineUninterceptedOrReturn { continuation ->
            COROUTINE_SUSPENDED
        }
    }
}

fun main() {
    val runBlockSeek = RunBlockSeek()
    runBlockSeek.m4()
}
