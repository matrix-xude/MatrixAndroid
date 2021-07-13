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

/**
 *    author : xxd
 *    date   : 2021/7/13
 *    desc   :
 */
class CancelCoroutineActivity : BaseTitleActivity() {

    private lateinit var viewBinding: CoroutineActivityCancelBinding
    private val buttonArray =
        listOf("开始携程1", "取消携程1", "站位", "站位", "站位", "站位", "站位", "站位", "站位", "站位", "站位", "站位", "站位")

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

                    override fun reflectNullViewHolder(layoutInflater : LayoutInflater): CoroutineItemCancelBinding {
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

    fun m1() {
        ToastUtil.showToast("111")
    }

    fun m2() {
        ToastUtil.showToast("222")
    }
}