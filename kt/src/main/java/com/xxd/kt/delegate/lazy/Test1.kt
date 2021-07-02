package com.xxd.kt.delegate.lazy

/**
 *    author : xxd
 *    date   : 2021/7/2
 *    desc   : 测试构造函数赋值
 */
class Test1(i :Int, val k : Int) {
    // j,k 赋值的地方一模一样，都是在构造函数内
    val j : Int = i
}