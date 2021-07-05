package com.xxd.common.base.activity

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 *    author : xxd
 *    date   : 2021/7/5
 *    desc   :
 */
class BaseLogicObserver(private val impl: IBaseLogic) : LifecycleObserver,IBaseLogic by impl {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        registerARouter()
    }

}