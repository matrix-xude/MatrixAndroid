package com.xxd.kt.tips

/**
 *    author : xxd
 *    date   : 2023/12/27
 *    desc   : @JvmOverloads的作用
 *    1.可以生成多参数的构造函数
 *    2.必须添加默认值才有效
 */
class MyView2 @JvmOverloads constructor(val a: String = "", val b: Int = 1, val c: Double = 0.0)