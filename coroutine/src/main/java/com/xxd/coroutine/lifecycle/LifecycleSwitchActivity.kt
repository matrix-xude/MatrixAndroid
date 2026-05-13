package com.xxd.coroutine.lifecycle

import androidx.fragment.app.Fragment
import com.xxd.common.fast.SimpleSwitchFragmentActivity
import com.xxd.coroutine.lifecycle.fragment.LifecycleFragment

/**
 *    author : xxd
 *    date   : 2026/5/13
 *    desc   : 研究 lifeCycle, 切换Fragment的Activity
 */
class LifecycleSwitchActivity : SimpleSwitchFragmentActivity() {

    val dataList = listOf("LifeCycle Fragment基础使用")

    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getPositionFragment(position: Int): Fragment {
        return when (position) {
            0 -> LifecycleFragment()
            else -> throw RuntimeException("当前无内容")
        }
    }

    override fun getTitleName(): CharSequence {
        return "LifeCycle功能总汇"
    }
}