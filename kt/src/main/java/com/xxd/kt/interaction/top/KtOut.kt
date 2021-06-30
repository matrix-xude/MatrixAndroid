package com.xxd.kt.interaction.top

/**
 *    author : xxd
 *    date   : 2021/6/25
 *    desc   : kotlin外部变量、函数反编译
 */

var outStr = "外部变量"

val outI by lazy {
    2
}

fun outShow() {
    println("我是kotlin外部方法，没在class内")
}

inline fun outShow2(info: String, show: (str: String) -> Unit) {
    show.invoke(info)
}
