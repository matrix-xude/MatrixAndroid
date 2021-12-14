package com.xxd.kt.basic.valvar

/**
 *    author : xxd
 *    date   : 2021/12/7
 *    desc   :
 */

var number = 0
val list: List<String>
    get() {
        println("当前执行次数${number++}")
        return listOf("a", "b", "a", "c", "a")
    }


fun main() {
    m1()
}

private fun m1() {
    repeat(5) {
        list.contains("a")
    }
}