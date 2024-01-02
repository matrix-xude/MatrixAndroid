package com.xxd.kt.basic.`for`

import com.xxd.kt.coroutines.basic.log

/**
 *    author : xxd
 *    date   : 2021/9/8
 *    desc   : 探寻for循环
 */

fun main() {
    m2()
}

private fun m0() {
    val list = listOf(1, 3, 5, 2, 7)

    // 在lambda for循环中，没有break continue,需要用标记服 xxx@ 来退出for循环
    run begin@{
        list.forEach {
            if (it == 3)
                return@begin
            println("$it")
        }
    }
}

private fun m1() {
    val list = listOf(1, 3, 5, 2, 7)
    // @forEach是默认标记，return@forEach 等于 continue 作用
    list.forEach {
        if (it == 3)
            return@forEach
        println("$it")
    }
}

private fun m2() {
    // subList()会映射出一个新的List，操作与原list关联
    val list = mutableListOf(1, 2, 4, 5, 6)
    val subList = list.subList(2, list.size)
    subList.clear()
    subList.add(11)
    log(list)
    log(subList)
}

private fun m3() {
    // .. 包含头，包含尾
    for (index in 1..1) {
        println("$index")
    }
}

private fun m4() {
    for (index in 10 downTo 0) {
        println("$index")
    }
}

private fun m5() {
    val list = listOf(1, 3, 5, 2, 7)
    val filter = list.indexOfFirst {
        it == 7
    }
    println(filter)
}