package com.xxd.design.strategy

/**
 *    author : xxd
 *    date   : 2023/11/24
 *    desc   :
 */

fun main() {
    val i = StrategyContext.useStrategy(AddStrategy(), 2, 1)
    val j = StrategyContext.useStrategy(SubtractStrategy(), 2, 1)
    println("i=$i j=$j")
}