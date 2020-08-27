package com.xxd.common.init

/**
 *    author : xxd
 *    date   : 2020/8/21
 *    desc   :
 */
interface IIsVisible {

    /**
     * 当前界面是否可见
     * @return true:可见 false:不可见
     */
    fun isVisibilityToUser() : Boolean
}