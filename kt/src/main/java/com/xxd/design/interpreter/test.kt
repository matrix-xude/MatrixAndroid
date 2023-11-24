package com.xxd.design.interpreter

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */

fun main() {
    val calculate = ExpressContext.calculate("7+2-5+1")
    println("7+2-5+1=$calculate")
}