package com.xxd.kt.data.copy

/**
 *    author : xxd
 *    date   : 2021/10/19
 *    desc   :
 */

fun main() {
    m2()
}

private fun m1() {
    val b1 = Bean1(1, "啥反", mutableListOf(1, 3, 5))
    val copy1 = b1.copy()
    val copy2 = b1.copy(id = 22)
    val copy3 = b1.copy(list = mutableListOf(9, 9, 9))

    println(copy1)
    println(copy2)
    println(copy3)
    println(b1 === copy1)
    println(b1.list === copy1.list)
    println(b1 === copy2)
    println(b1.list === copy2.list)
    println(b1 === copy3)
    println(b1.list === copy3.list)

}

private fun m2() {
    val list = listOf(Bean1(1, "覆盖", null), Bean1(2, "公共", null))
    val list2 = list.map {
        it.copy()
    }

    list2[0].name = "fdf"

    println(list)
    println(list2)

    println(list === list2)
    println(list[0] === list2[0])
    println(list[0].name === list2[0].name)
}