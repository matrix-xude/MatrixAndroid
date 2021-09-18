package com.xxd.common.costom.text

import android.text.TextPaint
import android.text.style.ClickableSpan

/**
 *    author : xxd
 *    date   : 2021/9/17
 *    desc   : 比较通用的可点击Span
 *    1. 去掉了下划线
 *    2. 可以设置文字的颜色
 *    tips: 有2个实现点击的要点，需要操作设置Span的TextView
 *    1. movementMethod = LinkMovementMethod.getInstance() 不设置无点击效果
 *    2. highlightColor = Color.TRANSPARENT 去掉点击后的背景颜色
 */
abstract class CustomClickSpan(val textColor: Int? = null) : ClickableSpan(){

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        textColor?.let { ds.color = it } // 改变文字颜色
        ds.isUnderlineText = false // 去掉下划线
    }
}