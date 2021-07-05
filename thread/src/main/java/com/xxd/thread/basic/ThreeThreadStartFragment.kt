package com.xxd.thread.basic

import android.annotation.SuppressLint
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.util.log.LogUtil
import com.xxd.thread.R
import kotlinx.android.synthetic.main.thread_fragment_three_start.*
import java.util.concurrent.Callable
import java.util.concurrent.FutureTask

/**
 *    author : xxd
 *    date   : 2020/8/28
 *    desc   : 线程的2种启动方式启动页面
 *      线程只有2种启动方式，源码级别
 *
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
    }

    @SuppressLint("SetTextI18n")
    private fun initListener() {
        bt1.setOnClickListener {
            TestThread().start()
        }
        bt2.setOnClickListener {
            Thread(TestRunnable()).start()
        }
        bt3.setOnClickListener {

            Thread(Runnable {
                LogUtil.d("${Thread.currentThread().name}:当前计算for循环的开始线程")
                // 第三种线程实现方法，带返回值，FutureTask，接收的参数即为新线程执行的方法
                val futureTask = FutureTask<Int>(TestCallable())
                // start之后已经开始执行Call中的方法
                Thread(futureTask).start()
                LogUtil.d("${Thread.currentThread().name}:FutureTask start之后")
                // 此方法会阻塞线程，如果在主线中调用，会anr
                val get = futureTask.get()
                LogUtil.d("${Thread.currentThread().name}:当前计算for循环的结果:$get")
//                LogUtil.d("${Thread.currentThread().name}:Future get()之后")
            }).start()
        }
    }

    /**
     * 第一种线程实现方法，继承Thread，覆写run()
     */
    inner class TestThread : Thread() {

        @SuppressLint("SetTextI18n")
        override fun run() {
            LogUtil.d("${currentThread().name}:通过继承Thread启动")
        }
    }

    /**
     * 第二种线程实现方法，实现Runnable，覆写run()
     */
    inner class TestRunnable : Runnable {
        @SuppressLint("SetTextI18n")
        override fun run() {
            LogUtil.d("${Thread.currentThread().name}:通过实现Runnable启动")
        }
    }

    /**
     * 第三种线程实现方法，带返回值，实现Callable，覆写call()
     */
    inner class TestCallable : Callable<Int> {
        @SuppressLint("SetTextI18n")
        override fun call(): Int {  // call方法执行的线程和run一样，都是新线程
            var i = 0
            repeat(10) {
                i += it
            }
            LogUtil.d("${Thread.currentThread().name}:当前计算for循环加法的过程", "thread")
            Thread.sleep(1000)
            return i
        }
    }
}