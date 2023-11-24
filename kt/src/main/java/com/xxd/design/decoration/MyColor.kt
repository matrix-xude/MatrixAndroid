package com.xxd.design.decoration

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */
class MyColor constructor(color: IColor) : IColorDecoration(color) {

    // 增加自己多出的方法
    fun draw() {
        println("我先绘制下图案")
    }
}