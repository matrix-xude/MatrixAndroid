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
import okhttp3.internal.wait
import java.util.concurrent.CancellationException
import java.util.concurrent.Future

/**
 *    author : xxd
 *    date   : 2021/7/13
 *    desc   :
 */
class CancelCoroutineActivity : BaseTitleActivity() {

    private lateinit var viewBinding: CoroutineActivityCancelBinding
    private val buttonArray =
        listOf("开始携程1", "查看job1", "查看job2", "取消job1", "取消job2", "站位", "站位", "站位", "站位", "站位", "站位", "站位", "站位")

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = CoroutineActivityCancelBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence {
        return "携程取消"
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
    private var job3 : Deferred<*>? = null
    fun m1() {
        job1 = GlobalScope.launch() {
            job3 = async {
                try {
                    while (true) {
                        delay(500)
                        log(index++)
                    }
                } catch (e: Exception) {
                    log("循环携程被取消 $e")
                }
            }
            try {
                job3!!.await()
            } catch (e: Exception) {
                log("调用join的携程被取消 $e")
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

    // 取消job1（外部携程）
    fun m4(){
        job1?.cancel()
    }

    // 取消job2（内部携程）
    fun m5(){
        job3?.cancel()
    }
}