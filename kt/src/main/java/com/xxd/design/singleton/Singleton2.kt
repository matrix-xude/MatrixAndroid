package com.xxd.design.singleton

/**
 *    author : xxd
 *    date   : 2023/11/17
 *    desc   :
 */
class Singleton2 private constructor() : Speak {


    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Singleton2()
        }
    }

    override fun speak() {
        println("懒汉式单例")
    }


}