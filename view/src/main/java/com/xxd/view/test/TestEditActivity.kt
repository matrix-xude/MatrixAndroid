package com.xxd.view.test

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.ViewGroup
import com.gyf.immersionbar.ktx.immersionBar
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.view.R
import com.xxd.view.databinding.ViewActivityTestEditBinding

/**
 *    author : xxd
 *    date   : 2022/10/14
 *    desc   :
 */
class TestEditActivity : BaseTitleActivity() {

    private lateinit var viewBinding : ViewActivityTestEditBinding

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = ViewActivityTestEditBinding.inflate(layoutInflater,rootView,false)
        rootView.addView(viewBinding.root)
    }

    override fun getTitleName(): CharSequence {
        return "测试"
    }

    override fun initView() {
        super.initView()

        immersionBar {
            statusBarColor(R.color.common_transparent)
            statusBarDarkFont(true)
            keyboardEnable(true)
        }
    }
}