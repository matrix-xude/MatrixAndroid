package com.xxd.kt.basic.data

/**
 *    author : xxd
 *    date   : 2022/4/24
 *    desc   :
 */

fun main() {
    testA()
}
fun testA(){
    val data1 = DataA(1L,"aaa")
    val data2 = DataA(1L,"aaa")
    println(data1 == data2)
    println(data1 === data2)
}