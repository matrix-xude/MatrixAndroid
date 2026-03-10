package com.xxd.kt.generic


/**
 *    author : xxd
 *    date   : 2026/3/10
 *    desc   : 使用处形变
 */
fun copy(from: MutableList<Any>, to: MutableList<Any>) {
    for (i in from.indices) to[i] = from[i]
}

// 使用处形变
fun copy2(from: MutableList<out Any>, to: MutableList<Any>) {
    for (i in from.indices) to[i] = from[i]
}

fun main() {
    val strings = mutableListOf("Apple", "Banana")
    val objects = mutableListOf<Any>()

    // 报错！MutableList<String> 不是 MutableList<Any> 的子类
    // copy(strings, objects)

    // 加上了使用处形变后，就不会报错
    copy2(strings, objects)
}