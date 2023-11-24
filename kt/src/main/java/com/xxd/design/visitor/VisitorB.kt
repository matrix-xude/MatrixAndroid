package com.xxd.design.visitor

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
class VisitorB : IVisitor {
    override fun visitElementA(elementA: ElementA) {
        println("我需要知道元素的name=${elementA.name} ,score=${elementA.score}")
    }
}