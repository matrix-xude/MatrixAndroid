package com.xxd.design.decoration

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */

fun main() {
    // 可以选择自己需要的装饰
    MyColor(ColorGreen()).run {
        draw()
        showColor()
    }
}