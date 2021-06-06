package com.xxd.common.base

import android.view.View
import android.view.ViewGroup
import com.xxd.common.databinding.CommonActivityBaseBinding

/**
 *    author : xxd
 *    date   : 2020/8/27
 *    desc   : 带公共title的activity，只处理actionbar与title的内容，
 *    业务逻辑不放此处！！
 */
abstract class BaseTitleActivity : BaseActivity() {

    protected lateinit var titlebinding: CommonActivityBaseBinding

    override fun viewBinding() {
        titlebinding = CommonActivityBaseBinding.inflate(layoutInflater, super.rootView, true)
    }

    override fun getRoot(): ViewGroup {
        return titlebinding.root
    }

    private fun init() {

        // 返回按钮
        titlebinding.ivTitleBack.visibility = if (showBackIcon()) View.VISIBLE else View.GONE
        titlebinding.ivTitleBack.setOnClickListener {
            onBackPressed()
        }

        // 标题
        titlebinding.tvTitleName.text = getTitleName()
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