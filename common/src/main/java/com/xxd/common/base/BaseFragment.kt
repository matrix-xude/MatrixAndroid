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

/**
 *    author : xxd
 *    date   : 2020/8/13
 *    desc   :
 */
abstract class BaseFragment : Fragment() {

    /**
     * 根view,可用来获取一切
     */
    lateinit var mRootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(getLayoutId(), container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(FragmentLifecycleObserver())
        initView()
    }

    abstract fun getLayoutId(): Int

    private fun initView() {

    }


    /**
     * 监听fragment生命周期的观察者
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