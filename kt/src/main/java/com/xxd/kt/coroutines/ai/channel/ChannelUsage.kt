package com.xxd.kt.coroutines.ai.channel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/**
 * 协程 Channel (通道) 示例，用于协程间的通信
 */
fun main() = runBlocking {
    val channel = Channel<Int>()

    // 生产者
    launch {
        for (x in 1..5) {
            channel.send(x * x)
            delay(100L)
        }
        channel.close() // 关闭通道，否则消费者会一直等待
    }

    // 消费者
    println("Consuming channel:")
    for (y in channel) {
        println("Received: $y")
    }

    println("\n--- Produce and Consume ---")

    // 使用 produce 构建器简化
    val squares = produce {
        for (x in 1..5) send(x * x)
    }
    squares.consumeEach { println("Produced: $it") }

    println("Done")
}
