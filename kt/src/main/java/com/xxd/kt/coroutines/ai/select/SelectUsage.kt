package com.xxd.kt.coroutines.ai.select

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select

/**
 * select 表达式示例：等待多个挂起函数的结果，并使用第一个可用的结果
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking {
    val producer1 = produce {
        delay(200)
        send("Result from producer 1")
    }
    val producer2 = produce {
        delay(100)
        send("Result from producer 2")
    }

    // select 会等待其中一个通道准备好
    val result = select<String> {
        producer1.onReceive { it }
        producer2.onReceive { it }
    }
    
    println("Selected: $result")
    
    // 清理
    producer1.cancel()
    producer2.cancel()
}
