package com.xxd.kt.function.ab

/**
 *    author : xxd
 *    date   : 2021/7/1
 *    desc   : A.(B) -> C
 */

// A.(B) -> C 函数
val block: Amber.(String) -> Amber = { str ->
    // 内部空间在Amber对象内，可以直接调用Amber的方法
    attack(str.toInt())
    this
}

val block2: Amber.(Int, Int) -> Unit = { i, j ->
    this.attack(i + j)
}

fun main() {
    // 使用方法1，函数引用block作为调用者
    block(Amber(), "12")
    // 使用方法2，接收函数的对象作为调用者，block作为方法
    Amber().block("13")

    Amber().block2(12, 13)

}