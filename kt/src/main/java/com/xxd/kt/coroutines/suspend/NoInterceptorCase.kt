package com.xxd.kt.coroutines.suspend

import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.*
import kotlin.coroutines.startCoroutine

/**
 *    author : xxd
 *    date   : 2026/4/1
 *    desc   : 无拦截器、纯手动实现协程调度
 */
val EMPTY = EmptyCoroutineContext

// 【关键点2】：自己写一个“调度函数”，不依赖拦截器
// 作用：把协程的恢复动作，提交到新线程
fun <T> dispatchContinuation(
    cont: Continuation<T>, result: Result<T>
) {
    // 调度逻辑：提交到新线程（你也可以提交到线程池、主线程、队列）
    thread { // 这里可以替换成任意线程调度方式
        println("【调度】协程在新线程恢复：${Thread.currentThread().name}")
        // 【核心】直接调用原始 Continuation 的 resumeWith
        cont.resumeWith(result)
    }
}

// 【关键点3】：挂起函数，手动调度、手动断开链条
suspend fun <T> suspendDispatch(block: () -> T): T = suspendCoroutineUninterceptedOrReturn { cont ->
    // 不立即恢复！
    // 直接把“恢复动作”交给我们自己的调度函数
    dispatchContinuation(cont, runCatching { block() })

    // 返回挂起标记 → 断开链条
    COROUTINE_SUSPENDED
}

// =============================================
// 测试运行
// =============================================
fun main() {
    println("主线程：${Thread.currentThread().name}")

    // 启动协程（无拦截器）
    suspend {
        println("协程开始：${Thread.currentThread().name}")

        // 执行调度：切换线程
        val result = suspendDispatch {
            println("异步执行：${Thread.currentThread().name}")
            100
        }

        println("协程恢复结果：$result  线程：${Thread.currentThread().name}")
        1
    }.startCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext get() = EMPTY
        override fun resumeWith(result: Result<Int>) = Unit
    })

    println("主线程执行完毕")
    Thread.sleep(1000)
}