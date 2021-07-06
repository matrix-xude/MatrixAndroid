package com.xxd.kt.coroutines.basic

/**
 *    author : xxd
 *    date   : 2021/7/6
 *    desc   :
 */

var lastPrintTime = 0L

/**
 * 打印携程信息专用
 */
fun printMessage(message: Any , printInterval: Boolean = true) {
    println(
        "(当前运行线程：${Thread.currentThread().name}; ${
            System.currentTimeMillis().apply {
                printInterval.takeIf { it }?.let { lastPrintTime }.takeIf {
                    it != 0L
                }?.let {
                    println("距离上一次打印间隔为 ${this - it}")
                }
                lastPrintTime = this
            }
        }) --> $message"
    )
}