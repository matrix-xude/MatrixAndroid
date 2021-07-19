package com.xxd.common.module.login

/**
 *    author : xxd
 *    date   : 2021/7/19
 *    desc   : 专门用来修改Login相关数据的接口
 */
interface ILoginChange {

    /**
     * 设置当前的登录状态
     * @param status true:登录 false:未登录
     */
    fun setLoginStatus(status : Boolean)
}