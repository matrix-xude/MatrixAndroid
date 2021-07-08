package com.xxd.kt.basic.companions

/**
 *    author : xxd
 *    date   : 2021/7/8
 *    desc   :
 */
class Companion1 {

    // 伴生类无论是否带Class Name，都只能有一个，匿名的companion object,类名就是Companion
    // companion object{} // 2个companion object报错

    companion object Key1{
        val myKey = "kkk"
    }
}