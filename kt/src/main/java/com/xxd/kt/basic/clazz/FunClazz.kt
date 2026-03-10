package com.xxd.kt.basic.clazz

/**
 *    author : xxd
 *    date   : 2026/3/8
 *    desc   :  (Int) -> Int 接口
 *              在 Kotlin 中，所有的函数类型（如 (Int) -> Int）本质上都是接口。
 *              当你写 class IntTransformer : (Int) -> Int 时，你是在告诉编译器：“这个类是一个函数类型，它的输入是 Int，输出也是 Int。”
 */
class FunClazz : (Int) -> Int {

    // invoke 能让函数被简写为括号形式
    override fun invoke(p1: Int): Int {
        return  p1 * 2
    }

    fun m1(){

    }
}

fun main() {
    val clazz = FunClazz()
    // 下面2个等价
    println(clazz(1))
    println(clazz.invoke(1))
}