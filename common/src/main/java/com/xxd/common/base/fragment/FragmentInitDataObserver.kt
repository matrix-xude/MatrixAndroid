package com.xxd.common.base.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.xxd.common.base.fragment.IFragmentInitData

/**
 *    author : xxd
 *    date   : 2020/8/21
 *    desc   : 此处真实调用数据加载，支持 Lifecycle 与 hide/show 状态变化
 */
class FragmentInitDataObserver(
    private val fragment: Fragment,
    private val impl: IFragmentInitData
) : DefaultLifecycleObserver, IFragmentInitData by impl {

    /**
     * 是否已经加载过数据，判断懒加载使用
     */
    private var isLoadedData = false

    override fun onCreate(owner: LifecycleOwner) {
        initDataImmediately()
    }

    override fun onResume(owner: LifecycleOwner) {
        checkAndInitData()
    }

    /**
     * 当 Fragment 显隐状态改变时（show/hide）手动调用
     */
    fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
            checkAndInitData()
        }
    }

    private fun checkAndInitData() {
        // 只有在 Fragment 处于 Resumed 状态且未隐藏时才触发加载
        if (fragment.isResumed && !fragment.isHidden) {
            if (!isLoadedData) {
                isLoadedData = true
                initDataLazy()
            }
            initDataEveryTime()
        }
    }
}
