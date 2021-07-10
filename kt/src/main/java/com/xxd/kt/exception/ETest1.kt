package com.xxd.kt.exception

import java.lang.Exception
import java.lang.RuntimeException

/**
 * author : xxd
 * date   : 2021/7/11
 * desc   : 测试Kotlin下的exception传递
 */

fun main() {
    try {
        m12()
    } catch (e: Exception) {
        println(e.message)
    }
}

fun m12() {
    m11()
}

fun m11() {
    throw RuntimeException("哈哈")
}