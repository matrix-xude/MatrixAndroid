package com.xxd.common.base

import android.view.View
import android.view.ViewGroup
import com.xxd.common.databinding.CommonActivityBaseBinding
import com.xxd.common.extend.binding

/**
 *    author : xxd
 *    date   : 2020/8/27
 *    desc   : 带公共title的activity，只处理actionbar与title的内容，
 *    业务逻辑不放此处！！
 */
abstract class BaseTitleActivity : BaseActivity() {

    protected val titleBinding: CommonActivityBaseBinding by binding(false)

    override fun setContentView(view: View?) {

        view?.let {
            val parent = it.parent
            if (parent is ViewGroup)
                parent.removeView(view)
            titleBinding.root.addView(
                it,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
        super.setContentView(titleBinding.root)
    }


    private fun init() {

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