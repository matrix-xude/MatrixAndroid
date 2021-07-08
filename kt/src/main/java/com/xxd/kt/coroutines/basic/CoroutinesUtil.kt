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
fun log(message: Any, printInterval: Boolean = false) {
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