package com.xxd.kt.generic

/**
 *    author : xxd
 *    date   : 2026/3/10
 *    desc   : kotlin 方法泛型与 java 一样
 */
class BasicsFunction {

    fun <T> singletonList(item: T): List<T> {
        return emptyList()
    }

    fun <T> T.basicToString(): String { // extension function
        return ""
    }

    fun m1(){
        // 完整调用
        val l = singletonList<Int>(1)
        // 省略写法
        val l2 = singletonList(1)
    }
}