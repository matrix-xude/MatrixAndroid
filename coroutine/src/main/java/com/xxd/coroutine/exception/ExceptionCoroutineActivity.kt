package com.xxd.coroutine.exception

import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.costom.decoration.CommonItemDecoration
import com.xxd.common.util.toast.ToastUtil
import com.xxd.coroutine.databinding.CoroutineActivityExceptionBinding
import com.xxd.coroutine.databinding.CoroutineItemExceptionBinding
import com.xxd.coroutine.myself.MyInterceptor
import com.xxd.coroutine.utils.log
import com.xxd.coroutine.utils.log2
import kotlinx.coroutines.*

/**
 * author : xxd
 * date   : 2021/7/11
 * desc   : 测试携程的异常机制
 */
class ExceptionCoroutineActivity : BaseTitleActivity() {

    private lateinit var viewBinding: CoroutineActivityExceptionBinding
    private val buttonArray =
        listOf(
            "带try catch",
            "不带try catch",
            "Global作用域1",
            "Global作用域2",
            "MyScope作用域1",
            "MyScope作用域2",
            "try catch位置",
            "异常后状态",
            "MyGlobal并发1",
            "MyGlobal并发2",
            "supervisorScope",
            "站位",
            "站位"
        )

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = CoroutineActivityExceptionBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence {
        return "携程异常"
    }

    override fun initView() {
        super.initView()

        viewBinding.rv1.apply {
            layoutManager = GridLayoutManager(this@ExceptionCoroutineActivity, 4)
            addItemDecoration(CommonItemDecoration().apply {
                boundary = 20
                interval = 20
                spanInterval = 30
            })
            adapter =
                object : BaseBindingQuickAdapter<String, CoroutineItemExceptionBinding>() {
                    override fun convert(
                        holder: BaseBindingViewHolder<CoroutineItemExceptionBinding>,
                        item: String
                    ) {
                        holder.binding.tv1.text = item
                    }
                }.apply {
                    setNewInstance(buttonArray.toMutableList())
                    setOnItemClickListener { _, _, position ->
                        // 反射调用 m1,m2……方法，方便
                        try {
                            this@ExceptionCoroutineActivity.javaClass.getDeclaredMethod("m${position + 1}")
                                .invoke(this@ExceptionCoroutineActivity)
                        } catch (e: Exception) {
                            ToastUtil.showToast("该方法还没有创建！")
                        }
                    }
                }
        }
    }

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            log2("handler捕获了异常：${throwable.message}", coroutineContext)
        }

    // try catch 会捕获异常，不会传到 coroutineExceptionHandler
    fun m1() {
        GlobalScope.launch(Dispatchers.Main + CoroutineName("1") + coroutineExceptionHandler) {
            try {
                delay(10)
                throw RuntimeException("异常-1")
            } catch (e: Exception) {
                log(e, this)
            }
        }
    }

    // 没捕获的异常会被 coroutineExceptionHandler 接收
    fun m2() {
        GlobalScope.launch(Dispatchers.Main + CoroutineName("2") + coroutineExceptionHandler) {
            delay(10)
            throw RuntimeException("异常-2")
        }
    }

    fun m3() {
        GlobalScope.launch(Dispatchers.Main + CoroutineName("3") + coroutineExceptionHandler) {
            log(1, this)
            try {
                GlobalScope.launch {
                    delay(10)
                    throw RuntimeException("异常3")
                }
            } catch (e: Exception) {
                log(e, this)
            }
        }
    }

    fun m4() {
        GlobalScope.launch(Dispatchers.Main + CoroutineName("4") + coroutineExceptionHandler) {
            log(1, this)
            GlobalScope.launch {
                delay(10)
                throw RuntimeException("异常4")
            }
        }
    }

    fun m5() {
        MyScope.launch(coroutineExceptionHandler) {
            log(1, this)
            try {
                MyScope.launch {
                    delay(10)
                    throw RuntimeException("异常3")
                }
            } catch (e: Exception) {
                log(e, this)
            }
        }
    }

    fun m6() {
        MyScope.launch(coroutineExceptionHandler) {
            log(1, this)
            MyScope.launch {
                delay(10)
                throw RuntimeException("异常4")
            }
        }
    }

    private var job1: Job? = null

    fun m7() {
        job1 = MyScope.launch(coroutineExceptionHandler) {
            log(1)
            try {  // 可以抓到 RuntimeException("async 抛出来的")
                coroutineScope {
                    launch {
                        try {  // 可以抓到一个 JobCancellationException 异常
                            delay(10000)
                            log(2)
                        } catch (e: Exception) {
                            log("3 $e")
                        }
                    }

                    try {  // 这里抓捕不到异常
                        async(coroutineExceptionHandler) {
                            delay(10)
                            throw RuntimeException("async 抛出来的")
                        }
                    } catch (e: Exception) {
                        log("5 $e")
                    }
                    log(4)
                }
            } catch (e: Exception) {
                log("6 $e")
            }
        }
    }

    fun m8() {
        log("Job1活跃？：${job1?.isActive}； Job1是否执行完毕：${job1?.isCompleted}; Job1是否取消：${job1?.isCancelled}")
    }

    fun m9() {
        MyScope.launch(coroutineExceptionHandler) {
            log(9, this)
            try {
                delay(2000)
            } catch (e: Exception) {
                log("9 $e", this)
            }
            log(91)
        }
    }

    fun m10() {
        MyScope.launch(coroutineExceptionHandler) {
            log(10, this)
            delay(1000)
            throw RuntimeException("异常10")
        }
    }

    fun m11() {
        MyScope.launch() {
            log(11, this)

                supervisorScope {
                    val job1 = async(coroutineExceptionHandler) {
                        delay(200)
                        log(2)
                        throw RuntimeException("哈哈哈错了")
                        "哈哈"
                    }
                    val job2 = async {
                        delay(2000)
                        log(3)
                        "呵呵"
                    }

                    log("11-2  ${job2.await()}")
                }
        }
    }


}

