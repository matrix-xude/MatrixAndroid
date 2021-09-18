package com.xxd.view.systemWidget.text

import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.TextView

/**
 *    author : xxd
 *    date   : 2021/9/18
 *    desc   : 本类是为了解决ClickSpan与整个TextView点击事件冲突的，
 *    在Android 8上，同时设置这2个事件，只响应TextView的点击事件
 *    在Android 10上，同时设置这2个事件，同时响应ClickSpan与TextView的点击事件
 *
 *    使用该类时，不应该给TextView设置点击事件，而应该给其父类设置点击事件；ClickSpan正常使用即可
 */
class CustomLinkMovementMethod : LinkMovementMethod() {

    override fun onTouchEvent(widget: TextView?, buffer: Spannable?, event: MotionEvent?): Boolean {
        val b = super.onTouchEvent(widget, buffer, event) // 让父类处理是否点击到了设置了点击事件的部分
        if (!b && event?.action == MotionEvent.ACTION_UP) {
            widget?.parent?.let {
                if (it is ViewGroup)
                    it.performClick()
            }
        }
        return b
    }
}