package com.xxd.design.interpreter

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
class SubtractExpression(left: IExpression<Int>, right: IExpression<Int>) : LeftRightExpression<Int>(left, right) {

    override fun interpret(): Int {
        return left.interpret() - right.interpret()
    }
}