package com.xxd.myself.navigation

import android.view.ViewGroup
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.myself.databinding.MyselfActivityNavigationBinding

/**
 *    author : xxd
 *    date   : 2024/3/16
 *    desc   : Navigation初次使用
 */
class NavigationActivity : BaseTitleActivity() {

    private lateinit var viewBinding: MyselfActivityNavigationBinding

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = MyselfActivityNavigationBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence {
        return "Navigation导航测试"
    }
}