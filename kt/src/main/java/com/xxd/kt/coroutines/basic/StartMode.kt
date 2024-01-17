package com.xxd.kt.coroutines.basic

import kotlinx.coroutines.*
import kotlin.concurrent.thread

/**
 *    author : xxd
 *    date   : 2021/7/6
 *    desc   : 协程启动方式
 */

suspend fun main() {
    start6()
}

// java启动线程的方式
fun start1() {
    val thread = object : Thread() {
        override fun run() {
            println("线程启动了")
        }
    }
    // 这句话比较多此一举，因为1.1有个stop，后来废弃了
    thread.start()
}

// kotlin对线程启动的优化
fun start2() {
    val thread = thread {
        println("优化线程启动，不用调用start()方法")
    }
}

// 协程默认直接启动,DEFAULT模式
fun start3() {
    GlobalScope.launch {
        println("直接启动了协程！")
    }
    Thread.sleep(100)
}

// 协程不直接启动，LAZY模式
suspend fun start4() {
    println(1)
    val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
        println("协程没有直接启动")
    }
    // 隐式启动协程
    job.join()
    println(2)
    Thread.sleep(100)
}

// 协程取消,ATOMIC模式
suspend fun start5(){
    log(1)
    val job = GlobalScope.launch(start = CoroutineStart.ATOMIC) {
        log(2)
        delay(100)
        log(3)
    }
    job.cancel()
    log(4)
    Thread.sleep(100)
}

// UNDISPATCHED，不是使用调度器，直接执行，直到第一个挂起，才使用调度器
suspend fun start6(){
    log(1)
    val job = GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
        log(2)
        delay(10)
        log(3)
    }
    log(4)
    job.join()
    // join之后，打印5的协程不再是main,待理解
    log(5)
}