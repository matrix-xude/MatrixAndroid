package com.xxd.kt.coroutines.suspend

import com.xxd.kt.coroutines.basic.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

/**
 *    author : xxd
 *    date   : 2026/3/20
 *    desc   : 
 */
class ThirdSuspend {
}

fun main() {
    m33()
    Thread.sleep(1000)
}

fun m31() {
    GlobalScope.launch(NoDefaultInterceptor()) {
        log(1)
        val job = launch {
            delay(100)
            log(2)
        }
        launch {
            delay(50)
            log(3)
        }
        log(4)
    }
}

// 打印3，证明父Job不会保存子Job的正常返回值
fun m32() {
    GlobalScope.launch(Dispatchers.Default) {
        log("start: ")

        runBlocking {

        }

        val i = withContext(Dispatchers.Unconfined) {
            withContext(Dispatchers.Main.immediate) {
                withContext(Dispatchers.IO) {
                    3
                }
            }
        }
        log(i)
    }
}

// 父Job会合并所有子Job的异常,但是有一个特殊的异常不会被合并，即 CancellationException
fun m33() {
    GlobalScope.launch(NoDefaultInterceptor() + CoroutineExceptionHandler { context, throwable -> log(throwable) }) {
        log("start: ")

        var deferred1: Deferred<*>? = null
        var deferred2: Deferred<*>? = null

        val deferred = async {
            // 改成supervisorScope结果不同哦
            coroutineScope {
                deferred1 = async {
                    try {
                        delay(100)
                        2 / 0
                    } catch (e: Exception) {
                        // 这里不单独处理 CancellationException ，会破坏协程的并发结构
                        log(e)
                    } finally {
                        // 模拟子Job在被取消的过程中，也抛出了一个非取消异常，是否会挂载到父Job _state中
                        throw NullPointerException("null指针了")
                    }
                }

                deferred2 = async {
                    delay(50)
                    throw IllegalArgumentException("参数不合法")
                }

                3
            }
        }

        try {
            val await = deferred.await()
            log("deferred.await() = $await")
        } catch (e: Exception) {
//            log("deferred.await() = $e")
            e.printStackTrace() // 这里可以看到2个异常
        }

        try {
            val await = deferred1?.await()
            log("deferred1.await() = $await")
        } catch (e: Exception) {
            log("deferred1.await().exception = $e")
        }

        try {
            val await = deferred2?.await()
            log("deferred2.await() = $await")
        } catch (e: Exception) {
            log("deferred2.await().exception = $e")
        }

        log("end")

        withTimeout(2000) {

        }
    }
}