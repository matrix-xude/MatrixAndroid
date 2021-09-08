package com.xxd.kt.basic.`for`

/**
 *    author : xxd
 *    date   : 2021/9/8
 *    desc   :
 */

fun main() {
    val list = listOf(1,3,5,2,7)
    list.forEach {
        if (it==3)
            return@forEach
        println("$it")
    }
}