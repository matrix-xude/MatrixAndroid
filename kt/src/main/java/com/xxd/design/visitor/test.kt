package com.xxd.design.visitor

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
fun main() {
    ElementContent().apply {
        visit(VisitorA())
        visit(VisitorB())
    }
}