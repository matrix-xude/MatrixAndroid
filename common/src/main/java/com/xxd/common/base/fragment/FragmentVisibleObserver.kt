package com.xxd.common.base.fragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.xxd.common.base.fragment.IFragmentVisible

/**
 *    author : xxd
 *    date   : 2020/8/21
 *    desc   : 当前fragment是否可见
 */
class FragmentVisibleObserver : LifecycleObserver, IFragmentVisible {

    /**
     * 当前fragment是否可见
     */
    private var isVisibilityToUser = false

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        isVisibilityToUser = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        isVisibilityToUser = true
    }

    override fun isVisibilityToUser(): Boolean {
        return isVisibilityToUser
    }
}