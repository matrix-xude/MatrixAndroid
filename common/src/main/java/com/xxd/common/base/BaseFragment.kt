package com.xxd.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.orhanobut.logger.Logger
import com.xxd.common.init.IFragmentInitData
import com.xxd.common.init.IIsVisible
import com.xxd.common.lifecycle.FragmentInitDataObserver
import com.xxd.common.lifecycle.FragmentVisibleObserver

/**
 *    author : xxd
 *    date   : 2020/8/13
 *    desc   :
 */
abstract class BaseFragment : Fragment(),
    IFragmentInitData,
    IIsVisible {

    /**
     * 真实判断是否可见的Observer
     */
    private lateinit var visibleObserver: FragmentVisibleObserver

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        lifecycle.addObserver(FragmentLifecycleObserver())
        lifecycle.addObserver(FragmentInitDataObserver(this))
        visibleObserver = FragmentVisibleObserver()
        lifecycle.addObserver(visibleObserver)
        initView()
    }

    open fun initView() {

    }

    override fun initDataImmediately() {
        Logger.d("initDataImmediately 被调用")
    }

    override fun initDataLazy() {
        Logger.d("initDataLazy 被调用")
    }

    override fun initDataEveryTime() {
        Logger.d("initDataEveryTime 被调用")
    }

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
//            Logger.d("FragmentLifecycleObserver.onAny")
        }
    }
}