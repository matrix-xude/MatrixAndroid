package com.xxd.kt.interaction.notnull

/**
 *    author : xxd
 *    date   : 2021/6/25
 *    desc   : Kotlin接收Java的字段
 */
fun main() {
    println(JavaProvider.str1?.length)
    println(JavaProvider.str2.length)
    // 此变量可能产生NullPointerException
    println(JavaProvider.str3.length)

    // 推荐方案
    val str3: String? = JavaProvider.str3
    println(str3?.length)

}
