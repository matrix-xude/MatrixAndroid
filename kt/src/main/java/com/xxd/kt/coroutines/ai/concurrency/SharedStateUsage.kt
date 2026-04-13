package com.xxd.kt.coroutines.ai.concurrency

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger

/**
 * 协程中的并发安全与共享状态示例
 */
fun main() = runBlocking {
    var counter = 0
    val mutex = Mutex()
    val atomicCounter = AtomicInteger()

    // 1. 使用 Mutex (互斥锁) 保证线程安全
    withContext(Dispatchers.Default) {
        val jobs = List(100) {
            launch {
                repeat(1000) {
                    mutex.withLock {
                        counter++
                    }
                }
            }
        }
        jobs.forEach { it.join() }
    }
    println("Counter with Mutex: $counter")

    // 2. 使用原子变量 (Atomic)
    withContext(Dispatchers.Default) {
        val jobs = List(100) {
            launch {
                repeat(1000) {
                    atomicCounter.incrementAndGet()
                }
            }
        }
        jobs.forEach { it.join() }
    }
    println("Counter with Atomic: ${atomicCounter.get()}")
}
