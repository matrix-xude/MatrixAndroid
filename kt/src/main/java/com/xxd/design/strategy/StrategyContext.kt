package com.xxd.design.strategy

/**
 *    author : xxd
 *    date   : 2023/11/24
 *    desc   :
 */
object StrategyContext {

    fun useStrategy(strategy: IStrategy, a: Int, b: Int):Int {
       return strategy.calculate(a, b)
    }
}