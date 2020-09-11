package com.xxd.thread.basic

import androidx.fragment.app.Fragment
import com.xxd.common.fast.SimpleSwitchFragmentActivity

/**
 *    author : xxd
 *    date   : 2020/8/28
 *    desc   :
 */
class ThreadBasicActivity : SimpleSwitchFragmentActivity() {

    private val dataList = listOf("3种线程的实现", "线程中断", "wait和notify", "", "")

    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getPositionFragment(position: Int): Fragment {
        return when (position) {
            0 -> ThreeThreadStartFragment()
            1 -> ThreadInterruptFragment()
            2 -> ThreadWaitNotifyFragment()
            else -> ThreeThreadStartFragment()
        }
    }

    override fun getTitleName(): CharSequence? {
        return "线程基础"
    }
}