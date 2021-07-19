package com.xxd.common.module.login

/**
 *    author : xxd
 *    date   : 2021/7/19
 *    desc   : 提供一个获取login数据，一个修改login数据
 */
interface ILoginOwner {

    /**
     * 获取Login数据
     */
    fun status() : ILoginStatus

    /**
     * 修改Login数据
     */
    fun change() : ILoginChange

}