package com.xxd.kt.coroutines.ai.exception

import kotlinx.coroutines.*

/**
 * 协程异常处理示例
 */
fun main() = runBlocking {
    // 1. CoroutineExceptionHandler: 处理未捕获的异常
    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception via handler")
    }

    val job = GlobalScope.launch(handler) {
        throw AssertionError()
    }
    job.join()

    println("\n--- SupervisorJob Example ---")

    // 2. supervisorScope: 子协程的失败不会导致父协程或其他兄弟协程失败
    supervisorScope {
        val first = launch {
            println("First child is failing")
            throw RuntimeException("First child failed")
        }

        val second = launch {
            delay(100L)
            println("Second child is still running")
        }
        
        first.join()
        second.join()
    }
    
    println("Main finished")
}
