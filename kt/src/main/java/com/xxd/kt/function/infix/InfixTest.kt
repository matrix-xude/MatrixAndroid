package com.xxd.kt.function.infix

/**
 *    author : xxd
 *    date   : 2026/3/8
 *    desc   : 中缀函数的2种写法
 *              1. 在类中
 *              2. 在扩展函数中
 */
internal class InfixTest {

    infix fun m1(i: Int) {
        println("m1 $i")
    }
}

infix fun Int.m2(s: String): Int {
    1.times(1)
    return this + s.toInt()
}


fun main() {
    InfixTest() m1 2

    println(2 m2 "5")
}