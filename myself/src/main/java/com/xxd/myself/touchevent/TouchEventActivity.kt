package com.xxd.myself.touchevent

import android.view.MotionEvent
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.extend.onClick
import com.xxd.myself.databinding.MyselfActivityTouchEventBinding

/**
 *    author : xxd
 *    date   : 2024/1/2
 *    desc   : 探讨事件分发机制
 */
class TouchEventActivity : BaseTitleActivity() {

    private lateinit var viewBinding: MyselfActivityTouchEventBinding

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = MyselfActivityTouchEventBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence {
        return "TouchEvent研究"
    }

    override fun initView() {
        super.initView()
        initListener()
    }

    private fun initListener() {
        viewBinding.myView1.onClick {
            Logger.d("myView1点击事件被触发")
        }
//        viewBinding.myViewGroup2.onClick {
//            Logger.d("myViewGroup2点击事件被触发")
//        }
//        viewBinding.myViewGroup1.onClick {
//            Logger.d("myViewGroup1点击事件被触发")
//        }


    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        TouchEventUtil.printfAction(ev!!.action, this::class.java.simpleName, "dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        TouchEventUtil.printfAction(event!!.action, this::class.java.simpleName, "onTouchEvent")
        return super.onTouchEvent(event)
//        return false
    }


}