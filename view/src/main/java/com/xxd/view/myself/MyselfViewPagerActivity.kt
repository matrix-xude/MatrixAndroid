package com.xxd.view.myself

import androidx.fragment.app.Fragment
import com.xxd.common.fast.SimpleSwitchFragmentActivity
import com.xxd.view.myself.flow.FirstViewGroupFragment
import com.xxd.view.myself.flow.MyFlowViewRecyclerFragment
import com.xxd.view.myself.image.CustomImageFragment
import com.xxd.view.myself.nine.NineControlFragment
import com.xxd.view.myself.nine.NineControlFragment2
import com.xxd.view.myself.oneline.OneLineLayoutFragment
import com.xxd.view.third.banner.BannerFragment
import com.xxd.view.third.banner.BannerFragment2
import com.xxd.view.third.indicator.MagicIndicatorFragment

/**
 *    author : xxd
 *    date   : 2021/8/16
 *    desc   : 自定义控件
 */
class MyselfViewPagerActivity : SimpleSwitchFragmentActivity() {

    private val dataList = listOf("自定义ViewGroup", "在Recycler中的表现", "九宫图", "九宫图2", "一行图","自定义的ImageView")

    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getPositionFragment(position: Int): Fragment {
        return when (position) {
            0 -> FirstViewGroupFragment()
            1 -> MyFlowViewRecyclerFragment()
            2 -> NineControlFragment()
            3 -> NineControlFragment2()
            4 -> OneLineLayoutFragment()
            5 -> CustomImageFragment()
            else -> throw RuntimeException("当前无内容")
        }
    }

    override fun getTitleName(): CharSequence {
        return "自定义View"
    }

}