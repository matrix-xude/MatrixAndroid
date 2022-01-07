package com.xxd.view.text

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatTextView
import com.xxd.common.util.log.LogUtil

/**
 *    author : xxd
 *    date   : 2021/11/29
 *    desc   : 一个拥有最大行数的TextView
 */
class MaxLineTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatTextView(context, attributeSet, defStyleAttr) {

    // 变化
    var layoutChange: ((Boolean) -> Unit)? = null

    private val onPreDrawListener = ViewTreeObserver.OnPreDrawListener {
        LogUtil.d(
            "OnPreDraw,当前时间：${System.currentTimeMillis()}, id=$id\n" +
                    "当前layout的种类，${layout::class.java.simpleName}\n" +
                    "当前布局的最大行数：${layout.lineCount}"
        )
        true
    }

    private val onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        if (layout == null)
            return@OnGlobalLayoutListener
        LogUtil.d(
            "OnGlobalLayout,当前时间：${System.currentTimeMillis()}\n" +
                    "当前layout的种类，${layout::class.java.simpleName}\n" +
                    "当前布局的最大行数：${layout.lineCount}"
        )
        layoutChange?.invoke(layout.lineCount > maxLines)

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        viewTreeObserver.addOnPreDrawListener(onPreDrawListener)
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
//        viewTreeObserver.removeOnPreDrawListener(onPreDrawListener)
        viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
    }
}