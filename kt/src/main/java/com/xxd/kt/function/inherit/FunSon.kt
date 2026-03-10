package com.xxd.kt.function.inherit

/**
 *    author : xxd
 *    date   : 2026/3/8
 *    desc   : 
 */
internal class FunSon : FunFather() {

    // 覆写父类的方法有默认值，子类不能再设置默认值
    override fun m1(i: Int, j: Int): Int {
        return i + j
    }
}

fun main() {
    // 调用也可以省略参数
    println(FunSon().m1())
    println(FunSon().m1(1))
}