package com.xxd.kt.basic.loop

/**
 *    author : xxd
 *    date   : 2023/12/27
 *    desc   : map or list of pairs
 *    for循环中可以析构成多个参数，前提是
 *    1.循环中的类必须有component函数
 *    2.析构函数出的参数 》= 接收的变量数量
 */

fun main() {
    traverseList2()
}

private fun traverseMap() {
    val map = mapOf(Pair(1, "a"), Pair(2, "b"))
    for ((k, v) in map) {
        println("$k->$v")
    }
}

private fun traverseList() {
    val list = listOf(Pair(1, "a"), Pair(2, "b"))
//    val list = listOf(1,2)  传入此参数报错
    for ((k, v) in list) {
        println("$k->$v")
    }
}

private fun traverseList2() {
    // Triple 是 data class，有component函数
    val list = listOf(Triple(1, "a", "aaa"), Triple(2, "b", "bbb"))
    for ((v0, v1, v2) in list) {
        println("$v0->$v1->$v2")
    }
}