package com.xxd.view.systemWidget

import androidx.fragment.app.Fragment
import com.xxd.common.fast.SimpleSwitchFragmentActivity
import com.xxd.view.systemWidget.text.TextViewFragment

/**
 *    author : xxd
 *    date   : 2020/8/20
 *    desc   :
 */
class SystemWidgetActivity : SimpleSwitchFragmentActivity() {

    private val dataList = listOf("ViewFlipper", "TextView测量", "TextViewLine", "TextViewLine2", "TextLengthFragment", "", "")
    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getPositionFragment(position: Int): Fragment {
        return when (position) {
            0 -> ViewFlipperFragment()
            1 -> TextViewFragment()
            2 -> TextViewLineFragment()
            3 -> TextViewLineFragment2()
            4 -> TextLengthFragment()
            else -> throw RuntimeException("当前无内容")
        }
    }

    override fun getTitleName(): CharSequence {
        return "系统控件研究"
    }

}