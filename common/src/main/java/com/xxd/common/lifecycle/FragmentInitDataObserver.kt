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
 *    用到了接口委托 IFragmentInitData by fragmentInit
 */
class FragmentInitDataObserver(private val fragmentInit: IFragmentInitData) : LifecycleObserver,
    IFragmentInitData by fragmentInit {

    /**
     * 是否已经加载过数据，判断懒加载使用
     */
    private var isLoadedData = false

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