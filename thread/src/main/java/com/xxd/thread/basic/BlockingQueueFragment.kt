package com.xxd.thread.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.util.log.LogUtil
import com.xxd.thread.databinding.ThreadFragmentBlockingQueueBinding
import java.util.concurrent.*

/**
 *    author : xxd
 *    date   : 2020/9/16
 *    desc   :
 */
class BlockingQueueFragment : BaseFragment() {

    private var executorService: ThreadPoolExecutor? = null
    private var executorService2: ExecutorService? = null

    private lateinit var viewBinding : ThreadFragmentBlockingQueueBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ThreadFragmentBlockingQueueBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()
        viewBinding.tv1.setOnClickListener {
            repeat(5) {
                executorService?.execute {
                    Thread.sleep(1000)
                    LogUtil.d("${Thread.currentThread().name}-我是任务${it + 1}")
                }
            }

        }
        viewBinding.tv2.setOnClickListener {
            repeat(10) {
                executorService?.execute {
                    Thread.sleep(1000)
                    LogUtil.d("${Thread.currentThread().name}-我是任务${it + 1}")
                }
            }
        }
        viewBinding.tv3.setOnClickListener {
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