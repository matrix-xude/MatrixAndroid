package com.xxd.design.chain

/**
 *    author : xxd
 *    date   : 2023/11/24
 *    desc   :
 */

fun main() {
    val handler = FirstHandler().apply {
        nextHandler = SecondHandler()
    }
    handler.disposeRequest(-13)
}