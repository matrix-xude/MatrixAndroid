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
class MyViewGroup2 @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attributeSet, defStyleAttr) {


    /***
     * return ture : 自己接收处理该次事件的传递，不触发任何 onTouchEvent 事件
     * return false : 不处理该次事件传递，让父类处理，父类调用了自己的onTouchEvent事件
     * return super.dispatchTouchEvent(ev)：调用自己的 onInterceptTouchEvent
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        TouchEventUtil.printfAction(ev!!.action, this::class.java.simpleName, "dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
//        return false
//        return true
    }

    /**
     *  return ture : 拦截该事件，调用自己的 onTouchEvent 事件
     *  return false : 不拦截该事件，让dispatchTouchEvent继续分发
     *  return super.dispatchTouchEvent(ev)：默认false
     *  特殊：一旦该view消费了事件，这个方法只会调用一次，不会再次调用
     */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        TouchEventUtil.printfAction(ev!!.action, this::class.java.simpleName, "onInterceptTouchEvent")
        return super.onInterceptTouchEvent(ev)
//        return true
    }

    /**
     *  return ture : 消费该事件，之后的事件也会传递到此处
     *  return false : 不消费该事件，之后的事件不再传递到此处
     *  特殊：如果不处理该事件，会向上传递，原理是该类 dispatchTouchEvent 返回了 false，让父类继续处理事件
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        TouchEventUtil.printfAction(event!!.action, this::class.java.simpleName, "onTouchEvent")
        return super.onTouchEvent(event)
//        return true
    }
}