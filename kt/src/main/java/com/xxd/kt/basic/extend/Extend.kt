package com.xxd.kt.basic.extend

/**
 *    author : xxd
 *    date   : 2021/10/21
 *    desc   : 当内部方法和扩展方法重名的时候，优先调用内部方法
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