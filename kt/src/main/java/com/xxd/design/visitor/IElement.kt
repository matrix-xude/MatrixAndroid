package com.xxd.design.visitor

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
interface IElement {

    fun acceptVisitor(visitor : IVisitor)
}