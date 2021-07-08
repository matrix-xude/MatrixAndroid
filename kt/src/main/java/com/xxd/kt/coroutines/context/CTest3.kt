package com.xxd.kt.coroutines.context

import com.xxd.kt.coroutines.basic.log
import kotlinx.coroutines.*
import kotlin.coroutines.resume
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
            val sm32 = sm32()
            log(sm32)
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

suspend fun sm32() = suspendCoroutine<String> {
    it.resume("哈哈哈")
}


