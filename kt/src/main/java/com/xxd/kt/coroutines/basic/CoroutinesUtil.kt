package com.xxd.kt.coroutines.basic

import kotlinx.coroutines.Job
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
fun log(message: Any, printInterval: Boolean = false) {
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
 * 打印协程信息专用，信息头包含协程上下文信息
 */
suspend fun logCoroutine(message: Any) {
    println("(CoroutineContext：${coroutineContext}) --> $message")
}

suspend inline fun Job.Key.currentJob() = coroutineContext[Job]