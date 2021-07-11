package com.xxd.coroutine.utils

/**
 * author : xxd
 * date   : 2021/7/11
 * desc   :
 */

var lastPrintTime = 0L

fun log(message: Any, printInterval: Boolean = true) {

    val interval: Long // 距离上一次打印间隔
    lastPrintTime = System.currentTimeMillis().apply {
        interval = this.minus(lastPrintTime.takeIf {
            it != 0L // 上一次为0，间隔为0
        } ?: 0)
    }

    println(
        "(当前运行线程：${Thread.currentThread().name}; ${
            printInterval.takeIf { it }.let {
                "距离上次间隔：${interval}毫秒"
            }
        }) --> $message"
    )
}