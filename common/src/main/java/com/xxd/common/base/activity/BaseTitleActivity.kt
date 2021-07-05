package com.xxd.common.base.activity

import android.view.View
import android.view.ViewGroup
import com.xxd.common.base.activity.BaseActivity
import com.xxd.common.databinding.CommonActivityBaseBinding

/**
 *    author : xxd
 *    date   : 2020/8/27
 *    desc   : 带公共title的activity，只处理actionbar与title的内容，
 *    业务逻辑不放此处！！
 */
abstract class BaseTitleActivity : BaseActivity() {

    protected lateinit var titleBinding: CommonActivityBaseBinding

    /**
     * final 终结父类抽象获取contentView方法
     */
    final override fun getContentViewBase(): View {
        titleBinding = CommonActivityBaseBinding.inflate(layoutInflater)
        provideBaseTitleRootView(titleBinding.root)
        return titleBinding.root
    }

    /**
     * 对子类提供当前viewBinding的root当做父布局
     */
    abstract fun provideBaseTitleRootView(rootView : ViewGroup)

    override fun initView() {
        super.initView()

        // 返回按钮
        titleBinding.ivTitleBack.visibility = if (showBackIcon()) View.VISIBLE else View.GONE
        titleBinding.ivTitleBack.setOnClickListener {
            onBackPressed()
        }

        // 标题
        titleBinding.tvTitleName.text = getTitleName()
    }

    /**
     * 是否显示返回按钮
     * @return true:显示  false:不显示
     */
    open fun showBackIcon(): Boolean {
        return true
    }

    /**
     * 返回标题的内容
     */
    abstract fun getTitleName(): CharSequence?
}