package com.xxd.kt.coroutines.suspend

import com.xxd.kt.coroutines.basic.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 *    author : xxd
 *    date   : 2026/3/20
 *    desc   : 
 */
class SecondSuspend {

    companion object {
        suspend fun m1() = suspendCoroutine<String> {
            Thread {
                Thread.sleep(1000)
                log("m1内部打印")
                it.resume("m1返回值")
            }.start()
        }

        suspend fun m2() = suspendCoroutine<String> {
            Thread {
                Thread.sleep(500)
                log("m2内部打印")
                it.resume("m2返回值")
            }.start()
        }
    }
}

fun main() {
    GlobalScope.launch(NoDefaultInterceptor()) {
        log(1)
        SecondSuspend.m1()
        log(2)
        SecondSuspend.m2()
        log(3)
    }

    // 在协程A挂起期间，启动另一个协程B
    Thread.sleep(100) // 确保协程A已经挂起
    GlobalScope.launch(NoDefaultInterceptor()) {
        log("协程B - 运行中") // 看这里是否由同一个 worker 打印
    }

    Thread.sleep(2000)
}