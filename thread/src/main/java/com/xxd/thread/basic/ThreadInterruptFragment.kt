package com.xxd.thread.basic

import com.xxd.common.base.BaseFragment
import com.xxd.thread.R
import kotlinx.android.synthetic.main.thread_fragment_interrupt.*

/**
 *    author : xxd
 *    date   : 2020/9/10
 *    desc   : 中断线程的方法
 */
class ThreadInterruptFragment : BaseFragment() {

    private var thread : Thread? = null

    override fun getLayoutId(): Int {
        return R.layout.thread_fragment_interrupt
    }

    override fun initView() {
        super.initView()
        bt1.setOnClickListener {
            thread?.interrupt()
        }
    }
}