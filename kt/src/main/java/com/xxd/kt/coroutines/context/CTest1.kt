package com.xxd.kt.coroutines.context

import com.xxd.kt.coroutines.basic.log
import kotlinx.coroutines.*

/**
 * author : xxd
 * date   : 2021/7/8
 * desc   : 探究CoroutineStart.UNDISPATCHED做了什么
 */
fun main() {
    m13()
}

fun m11(){
    GlobalScope.launch(context = MyInterceptor(), start = CoroutineStart.DEFAULT) {
        log(1)
    }
    log(2)
    Thread.sleep(100)
}

fun m12(){
    GlobalScope.launch(context = MyInterceptor(), start = CoroutineStart.UNDISPATCHED) {
        log(1)
    }
    log(2)
    Thread.sleep(100)
}

fun m13(){
    GlobalScope.launch(context =Dispatchers.IO+ MyInterceptor(), start = CoroutineStart.DEFAULT) {
        log(1)
        val launch = launch {
            log(3)
        }
        log(launch.isCompleted)
        val job = async(start = CoroutineStart.DEFAULT) {
            log(4)
//            Thread.sleep(1000)
            delay(1000)
            log(222222222)
            "Hello"
        }
//        Thread.sleep(1110)
        log("--------------------")
        val result = job.await()
        log(result)
    }

    log(2)
    Thread.sleep(2000)
    log(5)
}