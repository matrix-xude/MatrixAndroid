package com.xxd.view.systemWidget

import androidx.fragment.app.Fragment
import com.xxd.common.fast.SimpleSwitchFragmentActivity

/**
 *    author : xxd
 *    date   : 2020/8/20
 *    desc   :
 */
class SystemWidgetActivity : SimpleSwitchFragmentActivity() {

    private val dataList = listOf("ViewFlipper", "", "", "", "", "", "")
    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getPositionFragment(position: Int): Fragment {
        return when (position) {
            0 -> ViewFlipperFragment()
            1 -> ViewFlipperFragment()
            else -> throw RuntimeException("当前无内容")
        }
    }

    override fun getTitleName(): CharSequence {
        return "系统控件研究"
    }

}