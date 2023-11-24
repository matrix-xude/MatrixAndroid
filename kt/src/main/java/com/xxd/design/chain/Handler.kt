package com.xxd.design.chain

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */
abstract class Handler {

    // 用于判断和获取
    var nextHandler : Handler? = null

    // 该链需要处理的内容
    abstract fun disposeRequest(i : Int)

}