package com.xxd.kt.coroutines.context

import com.xxd.kt.coroutines.basic.log
import kotlinx.coroutines.*

/**
 * author : xxd
 * date   : 2021/7/8
 * desc   : 函数的挂起、恢复可能不在同一个线程
 */
fun main() {
    m21()
}

fun m21(){
    GlobalScope.launch(MyInterceptor()) {
        log(1)
        val job = async {
            delay(500)
            log(4)
            "Hello"
        }
        log(3)
        val result = job.await()
        log(result)
    }
    log(2)
    Thread.sleep(1000)
}

fun m22(){
    GlobalScope.launch(MyInterceptor()) {
        log(1)
        val job = async {
            delay(500)
            log(4)
            "Hello"
        }
        log(3)
        Thread.sleep(600) // 比上面的挂起delay挂起函数多休息100mm
        val result = job.await()

    }
    log(2)
    Thread.sleep(1000)
}

