package com.xxd.kt.generic

/**
 *    author : xxd
 *    date   : 2026/3/10
 *    desc   : kotlin泛型基础用法与java一样
 */
class BasicsClass<T>(val t : T) {
    val value = t
}

fun main() {
    // 完整写法
    val basicsClass: BasicsClass<Int> = BasicsClass<Int>(1)
    // 省略写法
    val basicsClass1 = BasicsClass(2)
}