package com.xxd.kt.coroutines.context

import com.xxd.kt.coroutines.basic.log
import kotlinx.coroutines.*
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * author : xxd
 * date   : 2021/7/8
 * desc   : 测试函数挂起时会不会进行调度
 */
fun main() {
    m31()
}

fun m31() {
    GlobalScope.launch(MyInterceptor()) {
        log(1)
        val job = async {
            log(m33())
            delay(500)
            log(4)
            "Hello"
        }
        log(3)
        val result = job.await()
        log(result)
    }
    log(2)
    Thread.sleep(2200)
}

suspend fun sm30() {

    log("suspend函数 被调用")
    Thread.sleep(500)
}

suspend fun m32() = suspendCoroutine<String> {
    it.resume("哈哈哈")
}

suspend fun m33()= suspendCancellableCoroutine { it ->
    it.resume(1111)
//    it.resumeWithException(RuntimeException("go go go"))
    it.invokeOnCancellation {throwable ->
        log("invokeOnCancellation被调用了$throwable")
    }
}


