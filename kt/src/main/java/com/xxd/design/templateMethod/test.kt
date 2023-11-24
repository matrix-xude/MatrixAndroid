package com.xxd.design.templateMethod

/**
 *    author : xxd
 *    date   : 2023/11/24
 *    desc   : 模板方法，继承基类就是最简单的demo
 */

fun main() {
    Child().apply {
        f1()
        f2()
        track()
    }
}

abstract class Father {

    fun track() {
        if (getTrue())
            println("哈哈哈")
    }

    abstract fun f1()

    abstract fun f2()

    abstract fun getTrue(): Boolean
}

class Child : Father() {
    override fun f1() {
        println("模板方法1")
    }

    override fun f2() {
        println("模板方法2")
    }

    override fun getTrue(): Boolean {
        return true
    }

}