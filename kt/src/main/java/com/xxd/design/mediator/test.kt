package com.xxd.design.mediator

/**
 *    author : xxd
 *    date   : 2023/11/24
 *    desc   :
 */

fun main() {
    val p1 = Person1()
    val p2 = Person2()
    val mediator = ConcreteMediator()
    // 互相持有对方
    p1.mediator = mediator
    p2.mediator = mediator
    mediator.register(p1)
    mediator.register(p2)

    // 开始通信
    p1.sendMsg("hello ，how are you")
    p2.sendMsg("你好啊")
}