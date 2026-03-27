package com.xxd.kt.coroutines.suspend

import com.xxd.kt.coroutines.basic.log
import com.xxd.kt.coroutines.context.MyInterceptor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 *    author : xxd
 *    date   : 2026/3/21
 *    desc   : 探寻 suspend 方法到底做了什么
 *              全局使不是用调度器，排除Dispatchers.Default影响
 */
internal class SuspendSeek {

    // 1. 没有挂起的 suspend
    suspend fun m1(a: Int, s: String): Int {
        delay(100)
        log("1. 没有挂起的 suspend")
        return 3
    }

    // 2. 没有挂起，但是开启Thread的 suspend
    suspend fun m2() {
        Thread {
            log("2. 没有挂起，但是开启Thread的 suspend")
        }.start()
    }

    var block: (() -> Unit)? = null

    // 3. 使用挂起，但是内部没有resume的 suspend
    suspend fun m3() = suspendCancellableCoroutine<String> {
        log("3. 使用挂起，但是内部没有resume的 suspend")
        // 没有 resume 会导致挂起后的代码永远无法触发，这里可以手动弄一个回调
        block = {
            it.resume("m3 成功 resume")
        }
    }

    fun m3Resume() {
        // 手动回调继续m3方法
        block?.invoke()
    }

    // 4. 使用挂起，但是内部直接resume的 suspend
    suspend fun m4() = suspendCancellableCoroutine {
        // 内部无切换线程，会一直在当前线程执行完该代码块（等于是suspend后，又立马执行）
        Thread.sleep(100)
        log("4. 使用挂起，但是内部直接resume的 suspend")
        it.resume("m4 成功 resume")
    }

    // 5. 使用挂起，并且开启了Thread,但是没有resume的 suspend
    suspend fun m5() = suspendCancellableCoroutine<String> {
        Thread {
            Thread.sleep(100)
            // 没有resume会导致挂起后的代码永远无法触发
            log("5. 使用挂起，并且开启了Thread,但是没有resume的 suspend")
        }.start()
    }

    // 6. 使用挂起，并且开启了Thread,resume的 suspend
    suspend fun m6() = suspendCancellableCoroutine {
        Thread {
            Thread.sleep(100)
            log("6. 使用挂起，并且开启了Thread,resume的 suspend")
            // 真成功挂起，并返回
            it.resume("m6 成功 resume")
        }.start()
    }

    // 7. 研究Continuation的传播
    fun m7() {
        GlobalScope.launch(MyInterceptor()) {  // 位置 1 ：创建的 StandaloneCoroutine，即是一个Job,也是 Coroutine
            // 位置 2: block代码块本身会生成一个Block$1类，Block$1 继承自 SuspendLambda，而 SuspendLambda 继承自 ContinuationImpl。
            delay(100)  // 位置 3 ：suspend方法delay方法必然有一个参数是Coroutine，调用时传入的是 MyContinuation(内部包含了位置2的 Block$1，2包含了1)

            launch {  // 位置4 ：新创建StandaloneCoroutine，即是一个Job,也是 Coroutine，与外部launch的Continuation无关
                delay(100)
            }
        }
    }

    // 8. launch block代码块本身会生成一个Block$1,反编译可以看到源码
    fun m8(i: Int, s: String, block: suspend (Int, String) -> String) {
        // 为了触发状态机生成，我们需要在一个协程作用域里启动它
        GlobalScope.launch {
            // 这里传入的 block 会被编译成一个类似 Function3 的对象
            // 而 launch 的整个大括号 {} 才是我们要找的 Block$1, 也就是内部那个Function2
            val result = block(i,s)
            println("Result: $result")
        }
    }
}

fun main() {
    val seek = SuspendSeek()

    GlobalScope.launch(NoDefaultInterceptor() + CoroutineExceptionHandler { _, throwable ->
        log("CoroutineExceptionHandler: $throwable")
    }) {
        log("start :")
        val result1 = seek.m1(1, "")
        log("after m1 : $result1")
        val result2 = seek.m2()
        log("after m2 : $result2")
//      val result3 =  seek.m3()
//       log("after m2 : $result3")
        val result4 = seek.m4()
        log("after m4 : $result4")
//        val result5 = seek.m5()
//        log("after m5 : $result5")
        val result6 = seek.m6()
        log("after m6 : $result6")
        log("end :")
    }

    GlobalScope.launch(NoDefaultInterceptor()) {
        log("另一个scope")
    }

//    seek.m3Resume()

    Thread.sleep(2000)
}