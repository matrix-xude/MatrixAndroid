package com.xxd.design.visitor

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
class ElementA(val age: Int, val name: String, val score: Int) : IElement {

    override fun acceptVisitor(visitor: IVisitor) {
        visitor.visitElementA(this)
    }


}