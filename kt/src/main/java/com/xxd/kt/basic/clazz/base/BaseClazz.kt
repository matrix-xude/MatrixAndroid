package com.xxd.kt.basic.clazz.base

/**
 *    author : xxd
 *    date   : 2026/3/9
 *    desc   : 
 */
internal class BaseClazz constructor() {

    var j: Int = 1

    val s: String = "a"

    // 次要构造函数，必须调用主构造函数
    constructor(i: Int) : this() {
        j = i
    }
}

fun main() {
    BaseClazz()
}