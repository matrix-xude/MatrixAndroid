package com.xxd.kt.function.inline

/**
 * author : xxd
 * date   : 2021/7/1
 * desc   : 内联函数在Java、Kotlin中的调用
 */

// 内联函数
inline fun attack(i: Int, block: (Int) -> Int): String {
    return block(i).toString()
}

// 普通函数
fun attack2(i: Int, block: (Int) -> Int): String {
    return block(i).toString()
}

fun main() {
    // kotlin调用内联函数、普通函数的区别
    attack(1) { i -> i + 1 }
    println("--------------------------")
    attack2(1) { i -> i + 1 }
}