package com.xxd.kt.function.extend

/**
 * author : xxd
 * date   : 2021/7/1
 * desc   : 扩展函数
 * 可以取到被扩展的函数对象，这是kotlin的特性，用对象去调用
 */

fun Source.plusPlus(num1: Int, num2: Int) {
    // 这里取到的this，是Source对象
    val plus = this.plus(num1, num2)
    val outPlus = plus + num2
    println("外部调用了plusPlus,多加了1次num2,结果为：$outPlus")
}
