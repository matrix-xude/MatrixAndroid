package com.xxd.common.base.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.orhanobut.logger.Logger

/**
 *    author : xxd
 *    date   : 2020/8/13
 *    desc   : BaseFragment，不做任何业务逻辑,只确定Fragment的结构（可移植到任意app）
 *    1. 提供一个initView（）的空实现,可以覆写
 *    2. 提供3个initData()方法 1.立即加载；2.懒加载；3.每次界面可见都加载
 *    3. 提供一个当前是否可见（处于onStart状态）的方法
 */
abstract class BaseFragment : Fragment(), IFragmentInitData, IFragmentVisible {

    /**
     * 真实判断是否可见的Observer
     */
    private lateinit var visibleObserver: FragmentVisibleObserver

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        visibleObserver = FragmentVisibleObserver()
        viewLifecycleOwner.lifecycle.addObserver(visibleObserver)
        initView()
//        lifecycle.addObserver(FragmentLifecycleObserver())
        viewLifecycleOwner.lifecycle.addObserver(FragmentInitDataObserver(this))
    }

    open fun initView() {}

    override fun initDataImmediately() {}

    override fun initDataLazy() {}

    override fun initDataEveryTime() {}

    override fun isVisibilityToUser(): Boolean {
        return visibleObserver.isVisibilityToUser()
    }

    /**
     * 监听fragment生命周期的观察者
     * @OnLifecycleEvent 标记的每个方法都是粘性的，可以接收到已经发生过的回调
     */
    class FragmentLifecycleObserver : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            Logger.d("FragmentLifecycleObserver.onCreate")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
            Logger.d("FragmentLifecycleObserver.onStart")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            Logger.d("FragmentLifecycleObserver.onResume")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            Logger.d("FragmentLifecycleObserver.onPause")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
            Logger.d("FragmentLifecycleObserver.onStop")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            Logger.d("FragmentLifecycleObserver.onDestroy")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        fun onAny() {
            Logger.d("FragmentLifecycleObserver.onAny")
        }
    }
}