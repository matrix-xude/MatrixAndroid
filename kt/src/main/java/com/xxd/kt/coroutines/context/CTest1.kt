package com.xxd.kt.coroutines.context

import com.xxd.kt.coroutines.basic.log
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

/**
 * author : xxd
 * date   : 2021/7/8
 * desc   : 探究CoroutineStart方式
 */
fun main() {
    m15()
}

fun m11() {
    GlobalScope.launch(context = MyInterceptor(), start = CoroutineStart.DEFAULT) {
        log(1)
    }
    log(2)
    Thread.sleep(100)
}

fun m12() {
    GlobalScope.launch(context = MyInterceptor(), start = CoroutineStart.UNDISPATCHED) {
        log(1)
    }
    log(2)
    Thread.sleep(100)
}

fun m13() {
    GlobalScope.launch(context = MyInterceptor(), start = CoroutineStart.UNDISPATCHED) {
        log(1)
        // delay本身就是一个suspend方法；1.所以开启方式失效 2：继承了MyInterceptor
        delay(100)
        log(3)
    }
    log(2)
    Thread.sleep(1000)
}

fun m14() {
    GlobalScope.launch(context = Dispatchers.IO + MyInterceptor(), start = CoroutineStart.DEFAULT) {
        log(1)
        val job1 = launch {
            log(3)
        }
        log(job1.isCompleted)
        val job = async(start = CoroutineStart.DEFAULT) {
            log(4)
            delay(1000)
            log(6)
            "Hello"
        }
        log("--------------------")
//        Thread.sleep(1110)  // 这里休眠超过1000，会让job.await不再等待，直接放回结果
        val result = job.await()
        log(result)
    }

    log(2)
    Thread.sleep(2000)
    log(5)
}

fun m15() {
    // 使用自己创建的线程池
    Executors.newFixedThreadPool(3) {
        Thread(it)
    }.asCoroutineDispatcher().use { // 注意这个use，可以自动关闭线程池，否则会一直运行
        GlobalScope.launch(context = it, start = CoroutineStart.DEFAULT) {
            log(0)
            delay(20) // 每次delay都会在调度器中找新的
            log(1)
            delay(50)
            log(3)
            delay(50)
            log(4)
        }
        log(2)
        Thread.sleep(500)
    }
}