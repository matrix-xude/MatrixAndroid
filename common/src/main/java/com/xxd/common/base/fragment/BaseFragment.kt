package com.xxd.common.base.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 *    author : xxd
 *    date   : 2020/8/13
 *    desc   : BaseFragment，不做任何业务逻辑,只确定Fragment的结构（可移植到任意app）
 *    1. 提供一个initView（）的空实现,可以覆写
 *    2. 提供3个initData()方法 1.立即加载；2.懒加载；3.每次界面可见都加载
 *    3. 提供一个当前是否可见（处于onStart状态）的方法
 */
abstract class BaseFragment : Fragment(), IFragmentInitData, IFragmentVisible {

    private lateinit var initDataObserver: FragmentInitDataObserver

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initDataObserver = FragmentInitDataObserver(this, this)
        viewLifecycleOwner.lifecycle.addObserver(initDataObserver)
    }

    open fun initView() {}

    override fun initDataImmediately() {}

    override fun initDataLazy() {}

    override fun initDataEveryTime() {}

    /**
     * 实现 IFragmentVisible 接口
     * 直接利用 Fragment 官方提供的 isVisible 属性
     */
    override fun isVisibilityToUser(): Boolean {
        // isResumed: 确保处于活跃生命周期 (onResume 之后, onPause 之前)
        // isVisible: fragment.isVisible 内部逻辑：isAdded() && !isHidden() && view != null && view.windowToken != null
        return isResumed && isVisible
    }

    /**
     * 处理 show/hide 导致的可见性变化，同步给数据加载观察者
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (::initDataObserver.isInitialized) {
            initDataObserver.onHiddenChanged(hidden)
        }
    }
}