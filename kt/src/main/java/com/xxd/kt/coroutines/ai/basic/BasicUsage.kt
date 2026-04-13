package com.xxd.kt.coroutines.ai.basic

import kotlinx.coroutines.*

/**
 * 协程基础用法示例
 */
fun main() = runBlocking {
    println("Main start: ${Thread.currentThread().name}")

    // 1. launch: 启动一个新协程，不阻塞当前线程，不返回结果 (Fire and forget)
    val job = launch {
        delay(1000L)
        println("World! (from launch) - ${Thread.currentThread().name}")
    }
    println("Hello,")

    // 2. async: 启动一个新协程，返回 Deferred 对象，可以通过 await() 获取结果
    val deferred = async {
        delay(500L)
        "Result from async"
    }
    
    // 等待 launch 完成
    job.join()
    
    // 等待并打印 async 结果
    val result = deferred.await()
    println(result)

    println("Main end")
}
