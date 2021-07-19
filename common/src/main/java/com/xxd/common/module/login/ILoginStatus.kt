package com.xxd.common.module.login

import androidx.lifecycle.LifecycleOwner

/**
 *    author : xxd
 *    date   : 2021/7/19
 *    desc   : 专门用来获取Login相关数据的接口
 */
interface ILoginStatus {

    /**
     * 单次判断当前是否登录
     * @return true:已登录  false:未登录
     */
    fun isLogin(): Boolean

    /**
     * 监听登录状态的改变,粘性事件，可以登录状态统一入口
     * @param lifecycleOwner 用来自动取消监听
     * @param observer 监听回调
     */
    fun observe(lifecycleOwner: LifecycleOwner, observer: (Boolean) -> Unit)

    /**
     * 监听登录状态的改变，粘性事件，可以登录状态统一入口
     * （一直监听，直到app被销毁，如果需要移除监听，使用 [observe] )
     * @param observer 监听回调
     */
    fun observeForever(observer: (Boolean) -> Unit)

}