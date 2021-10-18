package com.xxd.kt.delegate.claaz

import java.io.Serializable

/**
 *    author : xxd
 *    date   : 2021/7/1
 *    desc   : 一个简单的Kotlin类，防御，会被代理
 */
class BaseDefence : Defence,Serializable {

    private val declaration = "最好的攻击就是防御！！"

    override fun showDeclaration() {
        println(declaration)
    }

    override fun defenceAttack(attackAmount: Int) {
        println("${this.javaClass.simpleName} 我挡住了${attackAmount}次攻击")
    }
}