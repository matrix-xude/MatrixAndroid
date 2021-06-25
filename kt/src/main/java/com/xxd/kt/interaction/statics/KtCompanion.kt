package com.xxd.kt.interaction.statics

/**
 *    author : xxd
 *    date   : 2021/6/25
 *    desc   : companion object 反编译
 */
class KtCompanion {

    companion object {
        val i = 2
        val str = "aaa"
        fun show() = println("show")

        @JvmStatic
        fun show2()= println("show2")
    }

    fun show3(a: Int) {}

    /*
    Only members in named objects and companion objects can be annotated with '@JvmStatic'
    @JvmStatic
    fun show4(){}
    */
}