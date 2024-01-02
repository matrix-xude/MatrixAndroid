package com.xxd.myself.touchevent

import android.view.MotionEvent
import com.orhanobut.logger.Logger

/**
 *    author : xxd
 *    date   : 2024/1/2
 *    desc   :
 */
object TouchEventUtil {


    /**
     * 打印action事件
     */
    fun printfAction(action: Int, tag: String, touchName: String) {
        val actionString = when (action) {
            MotionEvent.ACTION_DOWN -> "ACTION_DOWN"
            MotionEvent.ACTION_MOVE -> "ACTION_MOVE"
            MotionEvent.ACTION_CANCEL -> "ACTION_CANCEL"
            MotionEvent.ACTION_UP -> "ACTION_UP"
            else -> action.toString()
        }
        Logger.d("${tag}->${touchName} 接收到事件: $actionString")
    }
}