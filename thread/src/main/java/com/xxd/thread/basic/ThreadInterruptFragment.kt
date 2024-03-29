package com.xxd.thread.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.util.log.LogUtil
import com.xxd.thread.databinding.ThreadFragmentInterruptBinding
import okhttp3.internal.wait

/**
 *    author : xxd
 *    date   : 2020/9/10
 *    desc   : 中断线程的方法有3种
 *      1. run() call() 方法执行完毕正常退出
 *      2. thread.stop() 暴力停止，已经过时，数据可能出问题，各种异常
 *      3. thread.interrupt()  在线程中打入一个中断标志，并不会马上中断线程，当线程被阻塞的时候
 *        （如：sleep(),wait(),join()），马上会抛出一个 InterruptedException,且中断标志被清除，
 *         线程中断。
 */
class ThreadInterruptFragment : BaseFragment() {

    private var thread: Thread? = null

    private lateinit var viewBinding : ThreadFragmentInterruptBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ThreadFragmentInterruptBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()
        viewBinding.bt1.setOnClickListener {
            thread = SleepThread()
            thread?.start()
        }
        viewBinding.bt2.setOnClickListener {
            thread?.interrupt()
        }
    }

    inner class SleepThread : Thread() {
        override fun run() {
            LogUtil.d("${currentThread().name}:sleep线程被阻断前")
            Thread.sleep(2000) // 此处interrupt 会抛出InterruptedException
            LogUtil.d("${currentThread().name}:sleep线程被阻断后")
        }
    }

    inner class WaitThread : Thread() {
        override fun run() {
            synchronized(this) {  // wait必须在能拿到锁
                LogUtil.d("${currentThread().name}:wait线程被阻断前")
                wait() //  // 此处interrupt 会抛出InterruptedException
                LogUtil.d("${currentThread().name}:wait线程被阻断后")
            }
        }
    }

    inner class JoinThread : Thread() {
        override fun run() {
            LogUtil.d("${currentThread().name}:join线程被阻断前")
            interrupt()
            val tempThread = SleepThread()
            tempThread.start()
            tempThread.join()
            LogUtil.d("${currentThread().name}:join线程被阻断后")
        }
    }

    inner class InterruptThread : Thread() {
        override fun run() {
            LogUtil.d("${currentThread().name}:interrupt状态为${isInterrupted}")
            interrupt() // 中断线程
            LogUtil.d("${currentThread().name}:interrupt状态为${isInterrupted}")
            if (isInterrupted) {
                LogUtil.d("${currentThread().name}:interrupt状态为${isInterrupted}")
            }
        }
    }

}