package com.xxd.kt.basic.extend

/**
 *    author : xxd
 *    date   : 2021/10/21
 *    desc   :
 */

fun main() {
    val test = ExtendTest1()
    test.m1()
    test.m2()
}

fun ExtendTest1.m1(){
    println("我是扩展ExtendTest1的方法 m1")
}

fun ExtendTest1.m2(){
    println("我是扩展ExtendTest1的方法 m2")
}