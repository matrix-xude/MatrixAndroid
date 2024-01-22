package com.xxd.coroutine.cancel

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.costom.decoration.CommonItemDecoration
import com.xxd.common.util.toast.ToastUtil
import com.xxd.coroutine.databinding.CoroutineActivityCancelBinding
import com.xxd.coroutine.databinding.CoroutineItemCancelBinding
import com.xxd.coroutine.myself.MyInterceptor
import com.xxd.coroutine.utils.log
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 *    author : xxd
 *    date   : 2021/7/13
 *    desc   :
 */
class CancelCoroutineActivity : BaseTitleActivity() {

    private lateinit var viewBinding: CoroutineActivityCancelBinding
    private val buttonArray =
        listOf("开始协程1", "查看job1", "查看job2", "取消job1", "取消job2", "supervisor空间", "withContext", "suspend", "suspend2", "站位", "站位", "站位", "站位")

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
            adapter = object : BaseBindingQuickAdapter<String, CoroutineItemCancelBinding>() {
                override fun convert(holder: BaseBindingViewHolder<CoroutineItemCancelBinding>, item: String) {
                    holder.binding.tv1.text = item
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
    private var job2: Deferred<*>? = null
    fun m1() {
        job1 = lifecycleScope.launch(Dispatchers.IO) {
            job2 = async {
                try {
                    while (true) {
//                        Thread.sleep(500) // 这样写，因为无挂起状态，永远无法结束Job
                        delay(500)
                        log(index++)
                    }
                } catch (e: Exception) {
                    log("循环协程被取消 $e")
                }
            }

            try {
                val number = job2!!.await()
                log(number)
            } catch (e: Exception) {
                log("await被取消 $e")
            }
        }
    }

    // 查看job1是否完成
    fun m2() {
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
        job2?.cancel()
    }

    // supervisorScope 空间
    fun m6() {
        job1 = lifecycleScope.launch() {
            supervisorScope {
                job2 = async {
                    try {
                        while (isActive) {
                            delay(2000)  // 必须需要使用 CoroutineContext 的地方才可能实现调度
                            log(index++)
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
                    val number = job2!!.await()
                    log(number)
                } catch (e: Exception) {
                    log("await被取消 $e")
                }
            }
        }
    }

    // 模拟网络取消
    fun m7() {
        job1 = lifecycleScope.launch(Dispatchers.Main) {

            log(1)
            try {
                val value = withContext(Dispatchers.IO) {// withContext返回的不是Job，而是直接执行，可以try catch
                    log(2)
                    delay(5000)
                    123
                }
                log("拿到了网络返回结果 $value")
            } catch (e: Exception) {
                log("被取消了")
            }
            log(3)

        }
    }

    // 使用suspend方法
    fun m8() {
        job1 = lifecycleScope.launch(Dispatchers.Main) {
            log(1)
            job2 = async(Dispatchers.IO) {
                getResult() // 此方法5秒后才会返回
            }
            try {
                // 5秒内取消job1 or job2,会导致该处抛出异常
                val result = job2!!.await()
                log("getResult=$result")
            } catch (e: Exception) {
                log("协程被取消异常 $e")
            }
        }
    }

    // 直接调用suspend，不使用调度器
    fun m9() {
        job1 = lifecycleScope.launch(MyInterceptor()) {
            log(1)
            try {
                val result= getResult() // 此方法5秒后才会返回
                log("getResult=$result")
            } catch (e: Exception) {
                log("协程被取消异常 $e")
            }
        }
    }

    // 把回调转为直接返回值
    private suspend fun getResult() = suspendCancellableCoroutine {
        val register = Register()
        val callback = object : CallBack<Int> {
            override fun onSuccess(t: Int) {
                it.resume(t)
            }

            override fun onFail(e: Exception) {
                it.resumeWithException(e)
            }
        }
        register.registerCallBack(callback)

        // 此方法会在协程取消时回调，这里需要做内部正在取消后的操作，比如：关闭数据流、移除回调等
        it.invokeOnCancellation {
            log("invokeOnCancellation被调用了")
            register.unregisterCallBack()
        }
    }

    class Register {

        private var callBack: CallBack<Int>? = null
        fun registerCallBack(callBack: CallBack<Int>) {
            this.callBack = callBack
            thread {  // 必须开启新线程，否则就不是挂起了
                Thread.sleep(5000)
                try {
                    this.callBack?.onSuccess(123) ?: log("当前callback已经null了")
                } catch (e: Exception) {
                    this.callBack?.onFail(e)
                }
            }
        }

        fun unregisterCallBack() {
            this.callBack = null
        }
    }

    // 模拟网络返回的回调
    interface CallBack<T> {

        fun onSuccess(t: T)

        fun onFail(e: Exception)
    }

}