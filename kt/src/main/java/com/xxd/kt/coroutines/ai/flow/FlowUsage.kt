package com.xxd.kt.coroutines.ai.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Kotlin Flow (冷流) 示例
 */
fun main() = runBlocking {
    // 1. 创建 Flow
    val numberFlow = flow {
        for (i in 1..3) {
            delay(100L) // 模拟异步操作
            emit(i) // 发送数据
        }
    }

    // 2. 收集 Flow
    println("Collecting flow:")
    numberFlow.collect { value -> println(value) }

    println("\n--- Flow Operators ---")

    // 3. 操作符示例
    (1..5).asFlow()
        .filter { it % 2 == 0 }
        .map { "Number $it" }
        .collect { println(it) }

    println("\n--- Flow on Dispatchers ---")

    // 4. flowOn: 改变上游数据发射所在的调度器
    flow {
        println("Emitting on ${Thread.currentThread().name}")
        emit(1)
    }
    .flowOn(Dispatchers.IO)
    .collect { value ->
        println("Collected $value on ${Thread.currentThread().name}")
    }
}
