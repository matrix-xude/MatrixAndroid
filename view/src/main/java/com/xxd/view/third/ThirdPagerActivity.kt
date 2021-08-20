package com.xxd.view.third

import androidx.fragment.app.Fragment
import com.xxd.common.fast.SimpleSwitchFragmentActivity
import com.xxd.view.recycler.adapter.DelegateMultiFragment
import com.xxd.view.recycler.adapter.MultiFragment
import com.xxd.view.recycler.adapter.provider.ProviderFragment
import com.xxd.view.third.banner.BannerFragment

/**
 *    author : xxd
 *    date   : 2021/8/16
 *    desc   :
 */
class ThirdPagerActivity: SimpleSwitchFragmentActivity() {

    private val dataList = listOf("Banner")

    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getPositionFragment(position: Int): Fragment {
        return when (position) {
            0 -> BannerFragment()
            else -> throw RuntimeException("当前无内容")
        }
    }

    override fun getTitleName(): CharSequence {
        return "第三方控件"
    }

}