package com.xxd.kt.basic.clazz.inner

/**
 *    author : xxd
 *    date   : 2026/3/10
 *    desc   : 
 */
class Outer {

    init {
        println("Outer init() 初始化")
    }
    private val bar: Int = 1

    // 反编译后是static,所以找不到bar
    class Nested {
        fun foo() = 2
    }

    // 反编译后是普通内部类
    inner class Inner {
        fun foo() = bar
    }
}

fun main() {
    // 不需要创建 Outer
    val nested = Outer.Nested()
    // 需要创建 Outer
    val inner = Outer().Inner()
}