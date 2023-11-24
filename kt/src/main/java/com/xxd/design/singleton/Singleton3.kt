package com.xxd.design.singleton

/**
 *    author : xxd
 *    date   : 2023/11/17
 *    desc   :
 */
class Singleton3 : Speak{

    companion object {

        val instance = SingletonHolder().holder

        class SingletonHolder {
            val holder = Singleton3()
        }
    }

    override fun speak() {
        println("静态内部类单例")
    }
}