package com.xxd.design.visitor

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
class VisitorA : IVisitor {
    override fun visitElementA(elementA: ElementA) {
        println("我需要知道元素的age=${elementA.age}, name=${elementA.name}")
    }
}