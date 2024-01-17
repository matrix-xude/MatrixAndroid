package com.xxd.coroutine.cancel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.orhanobut.logger.Logger
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.costom.decoration.CommonItemDecoration
import com.xxd.common.util.toast.ToastUtil
import com.xxd.coroutine.databinding.CoroutineActivityCancelBinding
import com.xxd.coroutine.databinding.CoroutineItemCancelBinding
import com.xxd.coroutine.utils.log
import kotlinx.coroutines.*
import kotlinx.coroutines.internal.resumeCancellableWith
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *    author : xxd
 *    date   : 2021/7/13
 *    desc   :
 */
class CancelCoroutineActivity : BaseTitleActivity() {

    private lateinit var viewBinding: CoroutineActivityCancelBinding
    private val buttonArray =
        listOf("开始协程1", "查看job1", "查看job2", "取消job1", "取消job2", "supervisor空间", "withContext", "suspend", "站位", "站位", "站位", "站位", "站位")

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = CoroutineActivityCancelBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence {
        return "协程取消"
    }

    override fun initView() {
        super.initView()

        viewBinding.rv1.apply {
            layoutManager = GridLayoutManager(this@CancelCoroutineActivity, 4)
            addItemDecoration(CommonItemDecoration().apply {
                boundary = 20
                interval = 20
                spanInterval = 30
            })
            adapter =
                object : BaseBindingQuickAdapter<String, CoroutineItemCancelBinding>() {
                    override fun convert(
                        holder: BaseBindingViewHolder<CoroutineItemCancelBinding>,
                        item: String
                    ) {
                        holder.binding.tv1.text = item
                    }

                    override fun reflectNullViewHolder(layoutInflater: LayoutInflater): CoroutineItemCancelBinding {
                        Logger.d("兜底方法被调用")
                        return CoroutineItemCancelBinding.inflate(layoutInflater)
                    }
                }.apply {
                    setNewInstance(buttonArray.toMutableList())
                    setOnItemClickListener { _, _, position ->
                        // 反射调用 m1,m2……方法，方便
                        try {
                            this@CancelCoroutineActivity.javaClass.getMethod("m${position + 1}")
                                .invoke(this@CancelCoroutineActivity)
                        } catch (e: Exception) {
                            ToastUtil.showToast("该方法还没有创建！")
                        }
                    }
                }
        }
    }

    private var index = 0
    private var job1: Job? = null
    private var job2: Job? = null
    private var job3: Deferred<*>? = null
    fun m1() {
        job1 = GlobalScope.launch() {
            job3 = async {
                try {
                    while (true) {
//                        Thread.sleep(500) // 这样写就像脱缰的野狗，无法控制了
                        delay(500)  // 必须需要使用 CoroutineContext 的地方才可能实现调度
                        log(index++)
                    }
                } catch (e: Exception) {
                    log("循环协程被取消 $e")
                }
            }


            try {
//                val await = job3!!.await()
                job3!!.join()
                log("抓不住异常")
            } catch (e: Exception) {
                log("调用join的协程被取消 $e")
            }
        }
    }

    // 查看job1是否完成
    fun m2() {
        Thread().interrupt()
        log("Job1活跃？：${job1?.isActive}； Job1是否执行完毕：${job1?.isCompleted}; Job1是否取消：${job1?.isCancelled}")
    }

    // 查看job2是否完成
    fun m3() {
        log("Job2活跃？：${job2?.isActive}； Job2是否执行完毕：${job2?.isCompleted}; Job2是否取消：${job2?.isCancelled}")
    }

    // 取消job1（外部协程）
    fun m4() {
        job1?.cancel()
    }

    // 取消job2（内部协程）
    fun m5() {
        job3?.cancel()
    }

    // supervisorScope 空间
    fun m6() {
        job1 = GlobalScope.launch() {
            supervisorScope {
                job3 = async {
                    try {
                        while (true) {
//                        Thread.sleep(500) // 这样写就像脱缰的野狗，无法控制了
                            delay(2000)  // 必须需要使用 CoroutineContext 的地方才可能实现调度
                            log(index++)
                            throw RuntimeException("哈哈哈")
                        }
                    } catch (e: Exception) {
                        log("循环协程被取消 $e")
                    }
                }

                // 被挂起可以取消
                async {
                    while (true) {
                        delay(500)  // 必须需要使用 CoroutineContext 的地方才可能实现调度
                        log("我是无忧无虑的循环")
                    }
                }

                try {
                    val await = job3!!.await()
//                    job3!!.join()
                    log("抓不住异常")
                } catch (e: Exception) {
                    log("调用join的协程被取消 $e")
                }
            }
        }
    }

    // 模拟网络取消
    fun m7() {
        job1 = GlobalScope.launch {


//            try {
//                val value = withContext(Dispatchers.Default) {
//                    Thread.sleep(5000)
//                    123
//                }
//                log("拿到了网络返回结果 $value")
//            } catch (e: Exception) {
//                log("被取消了")
//            }

            try {
                val job = async(Dispatchers.Default) {
                    Thread.sleep(5000)
                    log("我肯定是取消不了了之的")
                    123
                }
                log("拿到了网络返回结果 ${job.await()}")
            } catch (e: Exception) {
                log("被取消了 $e")
            }
        }
    }

    // 使用suspend方法
    fun m8() {
        job1 = GlobalScope.launch {


            try {
                log("suspend之前")
//                val get123 = async { get123() }
                val get123 = get123()
                log("suspend之后")
                log("拿到了网络返回结果 ${get123}")
            } catch (e: Exception) {
                log("被取消了 $e")
            }
        }
    }

    private suspend fun get123() : Int{
       return suspendCoroutine {
            log("开始执行suspend")

            Thread.sleep(2000)
//        delay(2000)
//        it.resumeWith(Result.success(123))
            it.resume(122)

//            it.resumeWithException(RuntimeException("hahaha"))
        }
    }

}