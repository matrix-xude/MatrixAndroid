package com.xxd.view.iconfont

import androidx.fragment.app.Fragment
import com.xxd.common.fast.SimpleSwitchFragmentActivity

/**
 *    author : xxd
 *    date   : 2021/8/20
 *    desc   :
 */
class IconFontActivity : SimpleSwitchFragmentActivity() {

    private val dataList = listOf("IconFontFirst", "IconFontSecond", "", "", "", "", "")

    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getPositionFragment(position: Int): Fragment {
        return when (position) {
            0 -> IconFontFirstFragment()
            1 -> IconFontSecondFragment()
            else -> throw RuntimeException("当前无内容")
        }
    }

    override fun getTitleName(): CharSequence {
        return "IconFont"
    }

}