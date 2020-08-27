package com.xxd.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.xxd.common.R
import kotlinx.android.synthetic.main.common_activity_base.*

/**
 *    author : xxd
 *    date   : 2020/8/27
 *    desc   : 带公共title的activity，只处理actionbar与title的内容，
 *    业务逻辑不放此处！！
 */
abstract class BaseTitleActivity : BaseActivity() {


    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.common_activity_base)
        LayoutInflater.from(this).inflate(getLayoutId(), llCommon)
        init()
    }

    private fun init() {

        // 返回按钮
        ivTitleBack.visibility = if (showBackIcon()) View.VISIBLE else View.GONE
        ivTitleBack.setOnClickListener {
            onBackPressed()
        }

        // 标题
        tvTitleName.text = getTitleName()
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