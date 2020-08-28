package com.xxd.thread.basic

import androidx.fragment.app.Fragment
import com.xxd.common.fast.SimpleSwitchFragmentActivity

/**
 *    author : xxd
 *    date   : 2020/8/28
 *    desc   :
 */
class ThreadBasicActivity : SimpleSwitchFragmentActivity() {

    private val dataList = listOf("3种线程的实现", "", "", "", "")

    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getPositionFragment(position: Int): Fragment {
        return when (position) {
            0 -> ThreeThreadStartFragment()
            else -> ThreeThreadStartFragment()
        }
    }

    override fun getTitleName(): CharSequence? {
        return "线程基础"
    }
}