package com.xxd.common.module.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

/**
 *    author : xxd
 *    date   : 2021/7/19
 *    desc   : 外部使用此类，只需要了解接口中的方法即可
 */
class LoginOwner private constructor() : ILoginOwner {

    companion object {
        val instance by lazy {
            LoginOwner()
        }
    }

    private val loginData = MutableLiveData<Boolean>()

    private val status = object : ILoginStatus {
        override fun isLogin(): Boolean {
            return loginData.value ?: false
        }

        override fun observe(lifecycleOwner: LifecycleOwner, observer: (Boolean) -> Unit) {
            loginData.observe(lifecycleOwner, observer)
        }

        override fun observeForever(observer: (Boolean) -> Unit) {
            loginData.observeForever(observer)
        }

    }

    private val change = object : ILoginChange {
        override fun setLoginStatus(status: Boolean) {
            loginData.value = status
        }
    }

    override fun status(): ILoginStatus {
        return status
    }

    override fun change(): ILoginChange {
        return change
    }
}