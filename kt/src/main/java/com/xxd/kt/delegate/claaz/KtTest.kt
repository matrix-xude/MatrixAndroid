package com.xxd.kt.delegate.claaz

/**
 *    author : xxd
 *    date   : 2021/7/1
 *    desc   : 研究 by 关键字
 */
fun main() {
    val baseDefence = BaseDefence()
    val myDefence = MyDefence(baseDefence)
    myDefence.defenceAttack(22)
    myDefence.showDeclaration()
}

