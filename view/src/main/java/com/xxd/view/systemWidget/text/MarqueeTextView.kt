package com.xxd.view.systemWidget.text

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView

/**
 *    author : xxd
 *    date   : 2022/1/5
 *    desc   :
 */
class MarqueeTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatTextView(context, attributeSet, defStyleAttr) {

    override fun isFocused(): Boolean {
        return true
    }
}