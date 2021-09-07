package com.xxd.view.third

import androidx.fragment.app.Fragment
import com.xxd.common.fast.SimpleSwitchFragmentActivity
import com.xxd.view.third.banner.BannerFragment
import com.xxd.view.third.banner.BannerFragment2
import com.xxd.view.third.indicator.MagicIndicatorFragment
import com.xxd.view.third.indicator.MyMagicIndicatorFragment

/**
 *    author : xxd
 *    date   : 2021/8/16
 *    desc   :
 */
class ThirdPagerActivity : SimpleSwitchFragmentActivity() {

    private val dataList = listOf("Banner", "Banner2", "MagicIndicator","MyMagicIndicatorFragment")

    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getPositionFragment(position: Int): Fragment {
        return when (position) {
            0 -> BannerFragment()
            1 -> BannerFragment2()
            2 -> MagicIndicatorFragment()
            3 -> MyMagicIndicatorFragment()
            else -> throw RuntimeException("当前无内容")
        }
    }

    override fun getTitleName(): CharSequence {
        return "第三方控件"
    }

}