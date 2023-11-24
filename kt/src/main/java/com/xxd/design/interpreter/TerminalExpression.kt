package com.xxd.design.interpreter

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
class TerminalExpression(private val info: String) : IExpression<Int> {

    override fun interpret(): Int {
        return info.toInt()
    }
}