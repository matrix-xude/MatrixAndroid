package com.xxd.design.flyweight

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */
interface IWrench {

    val key : Int

    // 不可共享的元素
    fun hit(unsharableElement : Int)
}