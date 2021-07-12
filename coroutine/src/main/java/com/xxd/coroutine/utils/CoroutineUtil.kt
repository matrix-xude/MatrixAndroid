package com.xxd.coroutine.utils

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * author : xxd
 * date   : 2021/7/11
 * desc   :
 */

var lastPrintTime = 0L

fun log(message: Any, contextScope: CoroutineScope? = null, printInterval: Boolean = true) {

    log2(message, contextScope?.coroutineContext, printInterval)

}

fun log2(message: Any, coroutineContext: CoroutineContext? = null, printInterval: Boolean = true) {

    val interval: Long // 距离上一次打印间隔
    lastPrintTime = System.currentTimeMillis().apply {
        interval = this.minus(lastPrintTime.takeIf {
            it != 0L // 上一次为0，间隔为0
        } ?: 0)
    }

    println(
        "(线程：${Thread.currentThread().name}；携程：${coroutineContext?.let { it[CoroutineName] }}; ${
            printInterval.takeIf { it }.let {
                "距离上次间隔：${interval}毫秒"
            }
        }) --> $message"
    )
}