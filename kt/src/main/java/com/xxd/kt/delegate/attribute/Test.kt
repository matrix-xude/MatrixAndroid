package com.xxd.kt.delegate.attribute

/**
 *    author : xxd
 *    date   : 2021/7/2
 *    desc   :
 */

// 使用委托属性
var d1 : Int by FirstDelegate()
// 根据被委托的类getValue、setValue自动推断
var d2 by FirstDelegate()
// 自动推断类型 与 当前定义的String类型不符合，报错
//var d3 : String by FirstDelegate()

fun main() {
    Ref().use()
    d1 = 22
    println(d1)
    println(d1)

    var d4 by SecondDelegate()
    d4 = "abc"
    println(d4)
    println(d4)
}