package com.xxd.view.material.flexboxlayout

import android.content.Context
import android.util.AttributeSet
import com.google.android.flexbox.FlexLine
import com.google.android.flexbox.FlexboxLayout


/**
 *    author : xxd
 *    date   : 2021/9/9
 *    desc   : FlexboxLayout设置maxLines之后，如果item超出maxLines，会全部罗列在最后一行，不符合需求；
 *    FlexBoxLayoutMaxLines支持设置MaxLines，并截断超出MaxLines的内容；
 */
class FlexboxLayoutMaxLine @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    FlexboxLayout(context, attributeSet, defStyleAttr) {

    private var maxLines = NOT_SET

    init {
        maxLine = super.getMaxLine()
        super.setMaxLine(NOT_SET)
    }

    override fun setMaxLine(maxLine: Int) {
        maxLines = maxLine
    }

    fun getMaxLines(): Int {
        return maxLines
    }

    /**
     * see [getMaxLines]
     */
    @Deprecated("", ReplaceWith("NOT_SET", "com.google.android.flexbox.FlexboxLayout.NOT_SET"))
    override fun getMaxLine(): Int {
        return NOT_SET
    }

    override fun getFlexLinesInternal(): List<FlexLine>? {
        val flexLines = super.getFlexLinesInternal()
        val size = flexLines.size
        if (maxLines in 1 until size) {
            flexLines.subList(maxLines, size).clear()
        }
        return flexLines
    }
}