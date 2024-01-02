package com.xxd.myself.touchevent

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 *    author : xxd
 *    date   : 2024/1/2
 *    desc   : 探讨事件分发机制
 */
class MyViewGroup1 @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attributeSet, defStyleAttr) {


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        TouchEventUtil.printfAction(ev!!.action, this::class.java.simpleName, "dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        TouchEventUtil.printfAction(ev!!.action, this::class.java.simpleName, "onInterceptTouchEvent")
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        TouchEventUtil.printfAction(event!!.action, this::class.java.simpleName, "onTouchEvent")
        return super.onTouchEvent(event)
//        return true
    }
}