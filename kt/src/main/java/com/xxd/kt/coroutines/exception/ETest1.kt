package com.xxd.kt.coroutines.exception

import com.xxd.kt.coroutines.context.NoDefaultInterceptor
import com.xxd.kt.coroutines.utils.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withTimeout

/**
 *    author : xxd
 *    date   : 2021/7/9
 *    desc   : coroutine exception 测试1
 */
suspend fun main() {
    m7()
}

/**
 * 测验 GlobalScope 内部是否自成一派，1.是否继承外部的CoroutineContext 2.是否往外抛出异常
 * 结论：GlobalScope开启的协程空间是独立的，既不继承外部的CoroutineContext,也不往外抛异常
 */
fun m1() {
    GlobalScope.launch(Dispatchers.Default + CoroutineName("外部Scope") + CoroutineExceptionHandler { _, exception ->
        log("CoroutineExceptionHandler抓到了异常")
        throw exception
    }) {
        log("1 ${coroutineContext[CoroutineName]}")
        try {
            GlobalScope.launch(Dispatchers.Default) {
                log("2 ${coroutineContext[CoroutineName]}")
                throw ArithmeticException("计算异常")
            }
        } catch (e: Exception) {
            log("try catch抓到了异常")
            throw e
        }
    }
    Thread.sleep(1000)
}

/**
 * 测验所有 CoroutineScope 内部是否自成一派，1.是否继承外部的CoroutineContext 2.是否往外抛出异常
 */
fun m2() {
    MyScope.launch(Dispatchers.Default + CoroutineName("外部Scope") + CoroutineExceptionHandler { _, exception ->
        log("CoroutineExceptionHandler抓到了异常")
        throw exception
    }) {
        log("1 ${coroutineContext[CoroutineName]}")
        try {
            MyScope.launch(Dispatchers.Default) {
                log("2 ${coroutineContext[CoroutineName]}")
                throw ArithmeticException("计算异常")
            }
        } catch (e: Exception) {
            log("try catch抓到了异常")
            throw e
        }
    }
    Thread.sleep(1000)
}

/**
 * 测试 coroutineScope 中的异常传播
 */
fun m3() {
    GlobalScope.launch(Dispatchers.IO) {
        log(1)
        try {
            coroutineScope { //① 第1处开启协程
                log(2)
                launch { // ② 第2处开启协程，在协程1内部
                    log(3)
                    launch { // ③ 第3处开启协程，在协程2内部
                        log(4)
                        delay(100)
                        log("--------------")
                        throw ArithmeticException("Hey!!")
                    }
                    log(5)
                }
                log(6)
                val job = launch { // ④ 第4处开启协程，在协程1内部
                    log(7)
                    delay(1000) // 如果这里try catch，也能补货异常
                    log("------333333------")
                }
                try {
                    log(8)
                    job.join()  // 优先于协程1执行,在父协程1被取消后，本协程4也回被取消JobCancellationException
                    log(9)
                } catch (e: Exception) {
                    log("10. $e")
                }
            }
            log(11)
        } catch (e: Exception) {
            log("12. $e")
        }
        log(13)
    }
    Thread.sleep(2000)
}

/**
 * 测试 supervisorScope 中的异常传播
 */
fun m4() {
    GlobalScope.launch(Dispatchers.IO) {
        log(1)
        try {
            supervisorScope { //① 第1处开启协程
                log(2)
                launch { // ② 第2处开启协程，在协程1内部
                    log(3)
                    launch { // ③ 第3处开启协程，在协程2内部
                        log(4)
                        delay(100)
                        log("--------------")
                        throw ArithmeticException("Hey!!")
                    }
                    log(5)
                }
                log(6)
                val job = launch { // ④ 第4处开启协程，在协程1内部
                    log(7)
                    delay(1000) // 如果这里try catch，也能补货异常
                    log("------333333------")
                }
                try {
                    log(8)
                    job.join()  // 优先于协程1执行,在父协程1被取消后，本协程4也回被取消JobCancellationException
                    log(9)
                } catch (e: Exception) {
                    log("10. $e")
                }
            }
            log(11)
        } catch (e: Exception) {
            log("12. $e")
        }
        log(13)
    }
    Thread.sleep(2000)
}

/**
 * 测试 CoroutineExceptionHandler 抓异常
 */
fun m5() {
    GlobalScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, e -> log("异常1 $e") }) {
        log(1)
        launch(CoroutineExceptionHandler { _, e -> log("异常2 $e") }) {
            log(2)
            launch(CoroutineExceptionHandler { _, e -> log("异常3 $e") }) {
                log(3)
                throw ArithmeticException("Hey!!")
            }
        }
        delay(100)
        log(4)
    }
    Thread.sleep(1000)
}

/**
 * 测试 CoroutineExceptionHandler 抓异常
 */
fun m6() {
    GlobalScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, e -> log("异常1 $e") }) {
        log(1)
        // 这里的作用域，使得取消外部协程失败，这里可以抓到异常
        supervisorScope {
            launch(CoroutineExceptionHandler { _, e -> log("异常2 $e") }) {
                log(2)
                launch(CoroutineExceptionHandler { _, e -> log("异常3 $e") }) {
                    log(3)
                    throw ArithmeticException("Hey!!")
                }
            }
        }
        delay(100)
        log(4)
    }
    Thread.sleep(1000)
}

/**
 * 测试 async
 */
suspend fun m7() {
    val deferred = GlobalScope.async(Dispatchers.IO + CoroutineExceptionHandler { _, e -> log("CoroutineExceptionHandler 1 $e") }) {
        1 / 0
        // 测试async的内部其它job是否会被取消，是否能抓到异常
        launch {
            try {
                delay(500)
                log(2)
            } catch (e: Exception) {
                log("异常2 $e")
            }
        }
    }

    try {
//        deferred.await()
         deferred.join() // 换成这个，就抓不到异常了
        log(1)
    } catch (e: Exception) {
        log("异常1 $e")
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