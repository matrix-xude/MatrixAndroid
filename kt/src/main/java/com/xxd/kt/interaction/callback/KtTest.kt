package com.xxd.kt.interaction.callback

/**
 *    author : xxd
 *    date   : 2021/6/25
 *    desc   :
 */

fun main() {

    // 调用Java的接口，可以使用lambda
    val cb = JavaTest.JavaCallBack { }

    // 调用Kotlin的接口，不可以私用lambda
    val cb2 = object : KtCallBack1 {
        override fun call() {
        }
    }

    // 调用Kotlin的 fun接口，可以使用lambda
    val cb3 = KtCallBack2 { }
}

interface KtCallBack1 {
    fun call()
}

fun interface KtCallBack2 {
    fun call()
}