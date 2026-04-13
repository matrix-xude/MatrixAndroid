package com.xxd.kt.coroutines.ai.cancellation

import kotlinx.coroutines.*

/**
 * 协程取消与超时示例
 */
fun main() = runBlocking {
    // 1. 取消协程
    val job = launch {
        try {
            repeat(1000) { i ->
                println("Job: I'm sleeping $i ...")
                delay(500L)
            }
        } catch (e: CancellationException) {
            println("Job was cancelled: ${e.message}")
        } finally {
            println("Job: I'm running finally")
        }
    }
    delay(1300L)
    println("Main: I'm tired of waiting!")
    job.cancelAndJoin()
    println("Main: Now I can quit.")

    println("\n--- Timeout Example ---")

    // 2. 超时处理
    try {
        withTimeout(2000L) {
            repeat(10) { i ->
                println("Timeout Task: $i")
                delay(500L)
            }
        }
    } catch (e: TimeoutCancellationException) {
        println("Timed out!")
    }

    // 3. withTimeoutOrNull: 超时返回 null 而不抛出异常
    val result = withTimeoutOrNull(1000L) {
        delay(2000L)
        "Done"
    }
    println("Result withTimeoutOrNull: $result")
}
