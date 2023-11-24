package com.xxd.design.interpreter

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   : 解释器接口
 */
interface IExpression<T> {

    fun interpret(): T
}