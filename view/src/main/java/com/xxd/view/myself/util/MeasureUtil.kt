package com.xxd.view.myself.util

import android.view.View
import com.xxd.common.util.log.LogUtil

/**
 *    author : xxd
 *    date   : 2021/8/27
 *    desc   :
 */
object MeasureUtil {

    /**
     * 打印 View.MeasureSpec 类型的log
     * @param measureSpec 测量值
     * @param preStr 前缀
     */
    fun logMeasureSpec(measureSpec: Int, preStr: String = "") {
        val mode = View.MeasureSpec.getMode(measureSpec)
        val size = View.MeasureSpec.getSize(measureSpec)
        val modeStr = when (mode) {
            View.MeasureSpec.AT_MOST -> "AT_MOST"
            View.MeasureSpec.EXACTLY -> "EXACTLY"
            View.MeasureSpec.UNSPECIFIED -> "UNSPECIFIED"
            else -> "未知的View.MeasureSpec.Mode"
        }
        LogUtil.d("${preStr}: mode=$modeStr,size=$size")
    }
}