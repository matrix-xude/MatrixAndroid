package com.xxd.design.facade

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   : 外观模式过于简单，就是封装方法而已
 */

fun main() {
    println(Facade.addSubtract(1,3,4))
}

private object Util1 {

    fun add(i: Int, j: Int): Int {
        return i + j
    }
}

private object Util2 {

    fun subtract(i: Int, j: Int): Int {
        return i - j
    }
}

private object Facade {

    fun addSubtract(i: Int, j: Int, k: Int): Int {
        return Util1.add(i, j).run {
            Util2.subtract(this, k)
        }
    }
}