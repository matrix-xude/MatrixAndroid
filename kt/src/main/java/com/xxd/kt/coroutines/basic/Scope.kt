package com.xxd.kt.coroutines.basic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *    author : xxd
 *    date   : 2021/7/6
 *    desc   : 携程的空间
 */

fun main() {
   m7()
}

// 第一个携程
fun m1(){
    GlobalScope.launch {
        printMessage("第一个携程")
    }
    printMessage("主线程执行，代码在GlobalScope.launch之后")
}

// 让主线程不销毁
fun m2(){
    GlobalScope.launch {
        printMessage("第一个携程")
    }
    printMessage("主线程执行，代码在GlobalScope.launch之后")
    Thread.sleep(10)
}

// 查看携程的资源准备时间，3-4mm
fun m3(){
    GlobalScope.launch {
        printMessage("第一个携程")
    }
    printMessage("主线程执行，代码在GlobalScope.launch之后")
    Thread.sleep(1)
}

// 使用coroutines调度器,Dispatchers.Unconfined当前线程执行
fun m4(){
    GlobalScope.launch(Dispatchers.Unconfined) {
        printMessage("Dispatchers.Unconfined 携程")
    }
    printMessage("主线程执行，代码在GlobalScope.launch之后")
}

// 使用coroutines调度器
fun m5(){
    GlobalScope.launch(Dispatchers.Default) {
        printMessage("Dispatchers.Default 携程")
        delay(100)
        GlobalScope.launch (Dispatchers.Unconfined){
            printMessage("Dispatchers.Unconfined 携程,内部")
        }
    }
    Thread.sleep(20)
    printMessage("主线程执行，代码在GlobalScope.launch之后")
    Thread.sleep(2000)
}

// 调度器IO、Default共享线程池
fun m6(){
    GlobalScope.launch(Dispatchers.IO) {
        printMessage("携程第1部分被触发")
    }
    printMessage("主线程执行，代码在携程第一部分之后")
    GlobalScope.launch {
        printMessage("携程第2部分被触发")
    }
    Thread.sleep(1000)
}

// Main调度器,报错，没有ain
fun m7(){
    GlobalScope.launch(Dispatchers.Main) {
        printMessage("Dispatchers.Main 携程")
    }
    printMessage("主线程执行，代码在GlobalScope.launch之后")
    Thread.sleep(2000)
}