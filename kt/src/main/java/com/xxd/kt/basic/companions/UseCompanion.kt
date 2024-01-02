package com.xxd.kt.basic.companions

/**
 *    author : xxd
 *    date   : 2021/7/8
 *    desc   : 调用Companion
 */

fun main() {
    // 自动获取到伴生类对象
    val companion1 = Companion1
    val companion4 = Companion1.Key1
    println(companion1 === companion4) // 打印true,就是同一个对象
    // 自动从伴生类中拿变量,下面2个等价
    Companion1.myKey
    Companion1.Key1.myKey

    // 匿名的伴生类，类名就是Companion,下面2个等价
    val companion2 = Companion2.key2
    val companion3 = Companion2.Companion.key2
    println(companion1 === companion4) // 打印true,就是同一个对象
}