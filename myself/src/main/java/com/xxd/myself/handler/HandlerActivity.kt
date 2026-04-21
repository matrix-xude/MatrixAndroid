package com.xxd.myself.handler

import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.os.MessageQueue.IdleHandler
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.extend.onClick
import com.xxd.myself.databinding.MyselfActivityHandlerBinding

/**
 *    author : xxd
 *    date   : 2024/1/5
 *    desc   : 探查handler机制
 */
class HandlerActivity : BaseTitleActivity() {

    private lateinit var viewBinding: MyselfActivityHandlerBinding

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = MyselfActivityHandlerBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence {
        return "Handler研究"
    }

    override fun initView() {
        super.initView()

        viewBinding.tvCreateHandler.onClick {
            newLooper()
        }
        // 发送一个Message,what为1-10的随机数
        viewBinding.tv1.onClick {
            handler!!.run {
                sendMessage(
                    obtainMessage().apply {
                        what = (1..10).random()
                    })
            }
        }
        // 退出子线程的Looper
        viewBinding.tv2.onClick {
            handler!!.run {
                sendMessage(
                    obtainMessage().apply {
                        what = 11
                    })
            }
        }
        // 监听MainLooper
        viewBinding.tv3.onClick {
            // Observer被@hide标记，需要反射创建
        }
        // IdleHandler使用
        viewBinding.tv4.onClick {
            val idleHandler = IdleHandler {
                Logger.d("我是IdleHandler,哈哈")
                true
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                handler!!.looper.queue.addIdleHandler(idleHandler)
            }
        }
    }

    companion object {
        @JvmStatic
        private var handler: Handler? = null // 为了防止内部类引用外部类，用static，这样无法实现不泄露
    }



    // 创建一个子线程的Looper
    private fun newLooper() {
        object : Thread("子线程Looper->") {
            override fun run() {
                Looper.prepare()  // 创建Looper,MessageQueue
                // 创建Handler, 匿名类创建会持有外部Activity引用，即使handler是static也无用，因为创建发生在Activity的上下文中
                handler = object : Handler(Looper.myLooper()!!) {
                    override fun handleMessage(msg: Message) {  // 接收消息
                        when (msg.what) {
                            in 1..10 -> Logger.d("我收到了消息what=${msg.what}")
                            11 -> Looper.myLooper()!!.quit() // 结束loop循环
                        }
                    }
                }
                Looper.loop()  // 开始轮询，这里面是死循环，除非结束loop，后面的代码是运行不到的
                Logger.d("Loop结束")  // 这行代码是执行不到的
            }
        }.start()

        Thread.sleep(100) // 保证子线程先创建完Looper，这里可能产生竞争问题，解决办法是用HandlerThread
        val message = handler!!.obtainMessage().apply {// 创建消息
            what = 1
        }
        handler!!.sendMessage(message)  // 发送消息
    }

    // 使用封装好的代码 HandlerThread 创建Looper 是最佳实践
    private var handlerThread: HandlerThread? = null

    private fun startWorker() {
        if (handlerThread != null) return // 避免重复创建

        handlerThread = HandlerThread("子线程Looper->").apply {
            start() // 启动线程
        }

        // Looper 已经准备好了，直接使用
        handler = Handler(handlerThread!!.looper) { msg ->
            when (msg.what) {
                in 1..10 -> {
                    Logger.d("我收到了消息what=${msg.what}")
                    true
                }
                else -> false
            }
        }
    }

    fun stopWorker() {
        // 关键：停止线程并清空消息，防止内存泄漏
        handler?.removeCallbacksAndMessages(null)
        handlerThread?.quitSafely()
        handlerThread = null
        handler = null
    }
}
