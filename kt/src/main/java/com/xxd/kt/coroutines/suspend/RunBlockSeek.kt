package com.xxd.kt.coroutines.suspend

import com.xxd.kt.coroutines.context.NoDefaultInterceptor
import com.xxd.kt.coroutines.utils.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED

/**
 *    author : xxd
 *    date   : 2026/4/7
 *    desc   : 
 */
fun main() {
    runBlocking {  // runBlocking内部实现了一个 EventLoop 调度器，所以打印为 3 1 2
        launch(NoDefaultInterceptor()) {
            log("launch进入") // 1
            delay(1000)
            log("子任务完成") // 2
        }
        yield()
        log("主程序运行中...") // 3
    }
}