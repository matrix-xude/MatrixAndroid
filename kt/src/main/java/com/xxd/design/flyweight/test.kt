package com.xxd.design.flyweight

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */

fun main() {
    val wrench1 = WrenchFactory.createWrench(1)
    val wrench2 = WrenchFactory.createWrench(2)
    val wrench3 = WrenchFactory.createWrench(3)
    val wrench4 = WrenchFactory.createWrench(1)

    wrench1.hit(3)
    println(wrench1 === wrench2)
    println(wrench1 === wrench4)
}