package com.xxd.kt.reflect

import kotlin.reflect.KClass

/**
 *    author : xxd
 *    date   : 2026/3/10
 *    desc   : 必须加入反射包才能使用 implementation(kotlin("reflect"))
 */
class Basic {

    // 注意，kotlin的反射与java不同，类型是KClass<T>
    val cKotlin : KClass<Basic> = Basic::class

    // 这才是java的反射
    val cJava : Class<Basic> = Basic::class.java

    val describe = "我是反射"

    fun m1(){
        println("invoke m1 : $describe")
    }
}

fun main() {
    val basic = Basic()
    // 与java一样， basic::class 与 Basic::class 返回值相同
    val klass = basic::class
    println("qualifiedName=${klass.qualifiedName}")
    println("simpleName=${klass.simpleName}")

}