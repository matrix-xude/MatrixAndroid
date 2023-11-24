package com.xxd.design.visitor

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   : 元素容器
 */
class ElementContent {

    val list = listOf<IElement>(ElementA(10,"Jack",38), ElementA(24,"张三",12))

    fun visit(visitor : IVisitor){
        list.forEach {
            it.acceptVisitor(visitor)
        }
    }
}