package com.xxd.kt.coroutines.basic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *    author : xxd
 *    date   : 2021/7/6
 *    desc   : 协程的空间
 */



fun main() {
   m7()
}

// 第一个协程
fun m1(){
    GlobalScope.launch {
        log("第一个协程")
    }
    log("主线程执行，代码在GlobalScope.launch之后")
}

// 让主线程不销毁
fun m2(){
    GlobalScope.launch {
        log("第一个协程")
    }
    log("主线程执行，代码在GlobalScope.launch之后")
    Thread.sleep(10)
}

// 查看协程的资源准备时间，3-4mm
fun m3(){
    GlobalScope.launch {
        log("第一个协程")
    }
    log("主线程执行，代码在GlobalScope.launch之后")
    Thread.sleep(1)
}

// 使用coroutines调度器,Dispatchers.Unconfined当前线程执行
fun m4(){
    GlobalScope.launch(Dispatchers.Unconfined) {
        log("Dispatchers.Unconfined 协程")
    }
    log("主线程执行，代码在GlobalScope.launch之后")
}

// 使用coroutines调度器
fun m5(){
    GlobalScope.launch(Dispatchers.Default) {
        log("Dispatchers.Default 协程")
        delay(100)
        GlobalScope.launch (Dispatchers.Unconfined){
            log("Dispatchers.Unconfined 协程,内部")
        }
    }
    Thread.sleep(20)
    log("主线程执行，代码在GlobalScope.launch之后")
    Thread.sleep(2000)
}

// 调度器IO、Default共享线程池
fun m6(){
    GlobalScope.launch(Dispatchers.IO) {
        log("协程第1部分被触发")
    }
    log("主线程执行，代码在协程第一部分之后")
    Thread.sleep(10)  // 保证2个launch中的代码不会同时执行
    GlobalScope.launch(Dispatchers.Default) {
        log("协程第2部分被触发")
    }
    Thread.sleep(1000)
}

// Main调度器,报错，没有ain
fun m7(){
    GlobalScope.launch(Dispatchers.Main) {
        log("Dispatchers.Main 协程")
    }
    log("主线程执行，代码在GlobalScope.launch之后")
    Thread.sleep(2000)
}