package com.xxd.kt.coroutines.ai.context

import kotlinx.coroutines.*

/**
 * 协程上下文与调度器示例
 */
fun main() = runBlocking {
    // Dispatchers.Default: 用于 CPU 密集型任务
    launch(Dispatchers.Default) {
        println("Default: I'm working in thread ${Thread.currentThread().name}")
    }

    // Dispatchers.IO: 用于阻塞性 I/O 任务
    launch(Dispatchers.IO) {
        println("IO: I'm working in thread ${Thread.currentThread().name}")
    }

    // withContext: 切换上下文并等待结果，常用于在协程中临时切换线程
    val result = withContext(Dispatchers.Default) {
        delay(100L)
        "Computed in Default"
    }
    println("Result: $result in ${Thread.currentThread().name}")
}
