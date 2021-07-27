package com.xxd.view.material

import androidx.fragment.app.Fragment
import com.xxd.common.fast.SimpleSwitchFragmentActivity
import com.xxd.view.material.tablayout.TabLayoutFragment

/**
 *    author : xxd
 *    date   : 2020/8/20
 *    desc   :
 */
class MaterialDesignActivity : SimpleSwitchFragmentActivity() {

    private val dataList = listOf("tabLayout", "", "", "", "", "", "")
    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getPositionFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabLayoutFragment()
            else -> throw RuntimeException("当前无内容")
        }
    }

    override fun getTitleName(): CharSequence {
        return "meter design 研究"
    }

}