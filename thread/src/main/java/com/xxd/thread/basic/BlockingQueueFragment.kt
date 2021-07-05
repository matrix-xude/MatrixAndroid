package com.xxd.thread.basic

import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.util.log.LogUtil
import com.xxd.thread.R
import kotlinx.android.synthetic.main.thread_fragment_blocking_queue.*
import java.util.concurrent.*

/**
 *    author : xxd
 *    date   : 2020/9/16
 *    desc   :
 */
class BlockingQueueFragment : BaseFragment() {

    private var executorService: ThreadPoolExecutor? = null
    private var executorService2: ExecutorService? = null

    override fun getLayoutId(): Int {
        return R.layout.thread_fragment_blocking_queue
    }

    override fun initView() {
        super.initView()
        tv1.setOnClickListener {
            repeat(5) {
                executorService?.execute {
                    Thread.sleep(1000)
                    LogUtil.d("${Thread.currentThread().name}-我是任务${it + 1}")
                }
            }

        }
        tv2.setOnClickListener {
            repeat(10) {
                executorService?.execute {
                    Thread.sleep(1000)
                    LogUtil.d("${Thread.currentThread().name}-我是任务${it + 1}")
                }
            }
        }
        tv3.setOnClickListener {
            executorService2 = Executors.newSingleThreadExecutor()
            repeat(10) {
                executorService2?.execute {
                    Thread.sleep(1000)
                    LogUtil.d("${Thread.currentThread().name}-我是单线程任务${it + 1}")
                }
            }
        }
    }

    override fun initDataLazy() {
        super.initDataLazy()
        initThreadPoolExecutor()


    }


    private fun initThreadPoolExecutor() {
        // 核心线程1个，最大3个，阻塞队列5个，只有当阻塞队列达到上限，才会启动到最大线程数
        executorService = ThreadPoolExecutor(
            1, 3, 10, TimeUnit.SECONDS,
            ArrayBlockingQueue<Runnable>(5), Executors.defaultThreadFactory(),
            ThreadPoolExecutor.DiscardOldestPolicy()
        )

    }
}