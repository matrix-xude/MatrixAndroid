package com.xxd.common.extend

import android.os.SystemClock
import android.view.View

/**
 * author : xxd
 * date   : 2021/7/11
 * desc   :
 */

data class ClickInterval(
    val intervalTime: Int,
    val lastTime: Long
)

val clickMap by lazy {
    mutableMapOf<Int, ClickInterval>()
}

/**
 * View扩展点击事件
 */
inline fun View.onClick(intervalTime: Int = 0, crossinline block: ((View) -> Unit)) {
    this.setOnClickListener {
        val clickInterval = clickMap[it.id]
        val elapsedRealtime = SystemClock.elapsedRealtime()
        if (clickInterval != null && elapsedRealtime - clickInterval.lastTime <= intervalTime)
            return@setOnClickListener

        clickMap[it.id] = ClickInterval(intervalTime, elapsedRealtime)
        block(it)
    }
}