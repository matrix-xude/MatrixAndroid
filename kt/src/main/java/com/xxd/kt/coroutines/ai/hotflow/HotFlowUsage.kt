package com.xxd.kt.coroutines.ai.hotflow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * StateFlow 与 SharedFlow (热流) 示例
 */
fun main() = runBlocking {
    // 1. StateFlow: 始终有值，且只发出的最新的值 (类似于 LiveData)
    val stateFlow = MutableStateFlow(0)
    
    val stateJob = launch {
        stateFlow.collect { println("StateFlow received: $it") }
    }

    delay(100L)
    stateFlow.value = 1
    stateFlow.value = 2
    delay(100L)

    println("\n--- SharedFlow Example ---")

    // 2. SharedFlow: 可以配置缓存，发出的事件可以被多个订阅者接收 (类似于 EventBus)
    val sharedFlow = MutableSharedFlow<String>(replay = 1)
    
    sharedFlow.emit("Message 1")
    
    val sharedJob = launch {
        sharedFlow.collect { println("SharedFlow received: $it") }
    }
    
    sharedFlow.emit("Message 2")
    
    delay(200L)
    stateJob.cancel()
    sharedJob.cancel()
}
