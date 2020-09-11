package com.xxd.thread.basic

import com.xxd.common.base.BaseFragment
import com.xxd.common.util.log.LogUtil
import com.xxd.thread.R
import kotlinx.android.synthetic.main.thread_fragment_wait_notify.*
import okhttp3.internal.notifyAll
import okhttp3.internal.wait

/**
 *    author : xxd
 *    date   : 2020/9/11
 *    desc   : wait(),notify(),notifyAll()必须在能拿到锁的情况下调用，否则报错
 */
class ThreadWaitNotifyFragment : BaseFragment() {

    /**
     * 持有生产物品的集合
     */
    val holdList = mutableListOf<String>()

    /**
     * 生产物品的编号
     */
    var i = 0

    companion object {
        // 最大的货物持有数量
        const val MAX_HOLD_NUM = 5
    }

    override fun getLayoutId(): Int {
        return R.layout.thread_fragment_wait_notify
    }

    override fun initView() {
        super.initView()
        bt1.setOnClickListener {
            ProduceThread().start()
            ConsumeThread().start()
        }
    }

    /**
     * 生产者
     */
    inner class ProduceThread : Thread() {
        override fun run() {
            while (true) {
                while (holdList.size >= MAX_HOLD_NUM) {
                    notifyAll()
                    wait()
                }
                // 生产者、消费者用同一把锁，类锁
                synchronized(ThreadWaitNotifyFragment::class.java) {
                    i++
                    val product = "产品编号$i"
                    holdList.add(product)
                    LogUtil.d("生产了产品：$product")
                }
            }
        }
    }

    /**
     * 消费者
     */
    inner class ConsumeThread : Thread() {
        override fun run() {
            while (true) {
                while (holdList.size < 0) {
                    notifyAll()
                    wait()
                }
                synchronized(ThreadWaitNotifyFragment::class.java) {
                    val product = holdList.removeFirst()
                    LogUtil.d("消费了产品：$product")
                }
            }
        }
    }
}