package com.xxd.kt.coroutines.suspend

import com.xxd.kt.coroutines.basic.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *    author : xxd
 *    date   : 2021/7/16
 *    desc   :
 */
suspend fun main() {
    GlobalScope.launch {
        log(1)
        m2()
        log(2)
        launch {
            log(3)
        }
    }.join()
}

suspend fun m1() = suspendCoroutine<String> {
    Thread.sleep(1000)
//    it.resume("哈哈")
    it.resumeWithException(RuntimeException("报错"))
}

suspend fun m2() = suspendCancellableCoroutine<String> {
    Thread.sleep(2000)
    it.resume("go go go")
//    it.resumeWithException(RuntimeException("报错"))
    it.invokeOnCancellation {
        log("当前协程被取消了")
    }
}