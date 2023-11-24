package com.xxd.design.singleton

/**
 *    author : xxd
 *    date   : 2023/11/16
 *    desc   :
 */
object Singleton1 : Speak {
    override fun speak() {
        println("饿汉式单例")
    }


}