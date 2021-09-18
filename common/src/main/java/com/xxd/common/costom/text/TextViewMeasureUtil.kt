package com.xxd.common.costom.text

import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint

/**
 *    author : xxd
 *    date   : 2021/9/17
 *    desc   : 测量TextView的各种数据的工具类
 */
object TextViewMeasureUtil {

    /**
     * 测试文字放入TextView中显示的行数
     * @param textPaint 画笔，一般直接从TextView中获取
     * @param content 需要填充的内容
     * @param width 画笔能使用的宽度，如果从TextView获取宽度，记得去掉padding
     */
    fun measureMaxLine(textPaint: TextPaint, content: CharSequence, width: Int): Int {
        // Builder为API23,只能使用废弃方法
        val staticLayout = StaticLayout(content, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1F, 0F, false)
        return staticLayout.lineCount
    }
}