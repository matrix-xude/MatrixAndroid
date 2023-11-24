package com.xxd.design.strategy

/**
 *    author : xxd
 *    date   : 2023/11/24
 *    desc   :
 */
class AddStrategy : IStrategy {
    override fun calculate(a: Int, b: Int): Int {
        return a + b
    }
}