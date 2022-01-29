package com.xxd.kt.basic.list

/**
 *    author : xxd
 *    date   : 2022/1/29
 *    desc   :
 */

fun main() {
    m2()
}

private fun m1() {
    val list1 = mutableListOf<String>()
    val list2 = mutableListOf<Int>()
    list2.add(1)
    println(list1 == list2)
}

private fun m2() {
    val o1: CC = AA("张三", 22)
    val o2: CC = AA("张三", 22)
    val o3: CC = BB("张三", 22)
    val o4: CC = BB("张三", 22)
    println(o1 == o2)
    println(o3 == o4)
    println(o1 == o3)

    val list1 = mutableListOf<CC>(o1, o3)
    val list2 = mutableListOf<CC>(o1, o3)
    println(list1.toString())
    println(list2.toString())
    println(list1 == list2)

    val list3 = mutableListOf(o3)
    val list4 = mutableListOf(o4)
    println(list3 == list4)

}

private interface CC {

}

private data class AA(
    val name: String,
    val age: Int
) : CC {
    val abc = "fff"
}

private class BB(
    val name: String,
    val age: Int
) : CC