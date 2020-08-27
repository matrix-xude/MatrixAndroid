package com.xxd.common.init

import android.content.Context

/**
 *    author : xxd
 *    date   : 2020/8/27
 *    desc   : 注册各种业务逻辑事件的接口，方便替换成自己app的各种注册
 */
interface IActivityRegister {

    fun register(context : Context)
}