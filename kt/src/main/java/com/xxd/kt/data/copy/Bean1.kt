package com.xxd.kt.data.copy

/**
 *    author : xxd
 *    date   : 2021/10/19
 *    desc   : 测试能不能覆写copy方法
 */
data class Bean1(
    val id: Int,
    var name: String,
    val list: MutableList<Int>?,
)