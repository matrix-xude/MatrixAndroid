package com.xxd.common.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.orhanobut.logger.Logger
import com.xxd.common.init.IFragmentInitData

/**
 *    author : xxd
 *    date   : 2020/8/21
 *    desc   : 此处真实调用数据加载，BaseFragment只是代理类
 */
class FragmentInitDataObserver(
    private val fragmentInit: IFragmentInitData?
) : LifecycleObserver,
    IFragmentInitData {

    /**
     * 是否已经加载过数据，判断懒加载使用
     */
    private var isLoadedData = false

    override fun initDataImmediately() {
        Logger.d("BaseFragment的 initDataImmediately 被触发")
        fragmentInit?.initDataImmediately()
    }

    override fun initDataLazy() {
        Logger.d("BaseFragment的 initDataLazy 被触发")
        fragmentInit?.initDataLazy()
    }

    override fun initDataEveryTime() {
        Logger.d("BaseFragment的 initDataEveryTime 被触发")
        fragmentInit?.initDataEveryTime()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        initDataImmediately()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (!isLoadedData) {
            isLoadedData = true
            initDataLazy()
        }
        initDataEveryTime()
    }
}