package com.xxd.kt.coroutines.basic

import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

/**
 *    author : xxd
 *    date   : 2021/7/6
 *    desc   : 工具类
 */

// 上一次打印的时间
private var lastPrintTime = 0L

/**
 * 打印线程信息
 * @param message 打印的信息
 * @param printInterval 距离上一次打印的间隔，用来记录延时
 */
fun log(message: Any?, printInterval: Boolean = true) {
    // 处理打印间隔问题
    var intervalInfo = ""
    System.currentTimeMillis().apply {
        if (printInterval) {  // 打印距离上一次的间隔
            lastPrintTime
                .takeIf { it != 0L }
                ?.let { intervalInfo = " ~~~距离上一次打印间隔=${this - it}" }
        }
        lastPrintTime = this
    }
    // 打印当前信息
    println("(当前线程：${Thread.currentThread().name}) --> $message $intervalInfo")
}

/**
 * 打印当前CoroutineContext中所有的 Element 信息
 */
fun printContextElements(context: CoroutineContext) {
    println("--- CoroutineContext Elements ---")
    // fold 类似于集合的 reduce，可以遍历所有 Element
    context.fold(Unit) { _, element ->
        println("Key: ${element.key} \t Value: $element")
    }
    println("---------------------------------")
}

/**
 * 打印协程信息专用，信息头包含协程上下文信息
 */
suspend fun logCoroutine(message: Any) {
    println("(CoroutineContext：${coroutineContext}) --> $message")
}

suspend inline fun Job.Key.currentJob() = coroutineContext[Job]