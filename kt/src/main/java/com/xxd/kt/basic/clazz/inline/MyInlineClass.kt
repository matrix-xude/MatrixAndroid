package com.xxd.kt.basic.clazz.inline

/**
 *    author : xxd
 *    date   : 2026/3/10
 *    desc   : 内联类，可以节省创建类的Heap开销。所以只能有1个变量
 */
@JvmInline
value class MyInlineClass(val name: String) {

    // 反编译后变成static方法
    fun email() = "$name@163.com"
}

fun main() {
    // 反编译后，String inlineClass = MyInlineClass.constructor-impl("abc"); 变成了String
    val inlineClass = MyInlineClass("abc")
    println(inlineClass.email())
}