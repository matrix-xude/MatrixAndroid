package com.xxd.kt.function.extend

/**
 * author : xxd
 * date   : 2021/7/1
 * desc   : 一个普通的Kotlin类，等待被扩展
 */
class Source {

    private val tag = "普通Kotlin类Source"

    fun plus(num1: Int, num2: Int): Int {
        println("$tag ---调用了plus()")
        return num1+num2
    }
}