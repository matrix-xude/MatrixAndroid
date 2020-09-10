package com.xxd.thread.basic

import androidx.core.net.toUri
import androidx.core.view.doOnPreDraw
import com.xxd.common.base.BaseFragment
import com.xxd.thread.R
import kotlinx.android.synthetic.main.thread_fragment_three_start.*

/**
 *    author : xxd
 *    date   : 2020/8/28
 *    desc   : 线程的3种启动方式启动页面
 */
class ThreeThreadStartFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.thread_fragment_three_start
    }

    override fun initView() {
        super.initView()
        initListener()
    }

    override fun initDataLazy() {
        super.initDataLazy()
        val a = ""
    }

    private fun initListener() {
        bt1.setOnClickListener {

        }
        bt2.setOnClickListener {

        }
        bt3.setOnClickListener {

        }
    }


    /**
     * 第一种线程实现方法，继承Thread，覆写run()
     */
    class TestThread : Thread() {

        override fun run() {

        }
    }
}