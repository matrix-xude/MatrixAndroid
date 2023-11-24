package com.xxd.design.interpreter

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
abstract class LeftRightExpression<T>(val left: IExpression<T>, val right: IExpression<T>) : IExpression<T> {


}