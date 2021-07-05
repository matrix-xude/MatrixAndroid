package com.xxd.thread.basic

import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.util.log.LogUtil
import com.xxd.thread.R
import kotlinx.android.synthetic.main.thread_fragment_wait_notify.*
import okhttp3.internal.notifyAll
import okhttp3.internal.wait

/**
 *    author : xxd
 *    date   : 2020/9/11
 *    desc   : wait(),notify(),notifyAll()必须在能拿到锁的情况下调用
 *    (所以wait，notify必须使用synchronized的锁)，否则报错
 *    线程的生命周期与当前Activity，Fragment无关，所以线程的使用需要注意生命周期
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

    var produceThread: Thread? = null
    var consumeThread: Thread? = null
    var threadInterruptFlag = false

    companion object {
        // 最大的货物持有数量
        const val MAX_HOLD_NUM = 5
    }

    override fun getLayoutId(): Int {
        return R.layout.thread_fragment_wait_notify
    }

    override fun initView() {
        super.initView()
        produceThread = ProduceThread()
        consumeThread = ConsumeThread()
        bt1.setOnClickListener {
            produceThread?.start()
            consumeThread?.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        threadInterruptFlag = true
    }

    /**
     * 生产者
     */
    inner class ProduceThread : Thread() {
        override fun run() {
            while (!threadInterruptFlag) {
                // 生产者、消费者用同一把锁，类锁
                synchronized(holdList) {
                    while (holdList.size >= MAX_HOLD_NUM) {
                        holdList.notifyAll()
                        holdList.wait()
                    }
                    i++
                    val product = "产品编号$i"
                    holdList.add(product)
                    LogUtil.d("生产：$product")
                }
                sleep(1000)
            }
        }
    }

    /**
     * 消费者
     */
    inner class ConsumeThread : Thread() {
        override fun run() {
            sleep(2000)
            while (!threadInterruptFlag) {
                synchronized(holdList) {
                    while (holdList.size <= 0) {
                        holdList.notifyAll()
                        holdList.wait()
                    }
                    val product = holdList.removeFirst()
                    LogUtil.d("消费了：$product")
                }
                sleep(1000)
            }
        }
    }
}