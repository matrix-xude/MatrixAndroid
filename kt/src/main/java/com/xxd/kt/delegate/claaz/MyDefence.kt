package com.xxd.kt.delegate.claaz

/**
 *    author : xxd
 *    date   : 2021/7/1
 *    desc   : 委托模式
 */
class MyDefence(impl: Defence) : Defence by impl {

    override fun showDeclaration() {
        println("我的防御99999")
    }
}