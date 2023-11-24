package com.xxd.design.builder

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */

fun main() {
    val dream = Dream.Builder().apply {
        target = 2
        struggle = 0.99f
    }.build()
    println("梦想目标：${dream.target}个，努力程度${dream.struggle}")
}