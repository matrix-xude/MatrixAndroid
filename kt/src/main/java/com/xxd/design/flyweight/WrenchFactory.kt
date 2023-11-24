package com.xxd.design.flyweight

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */
object WrenchFactory {

    private val map = mutableMapOf<Int, IWrench>()

    fun createWrench(key: Int): IWrench {
        if (map[key] == null) {
            map[key] = object : IWrench {
                override val key: Int
                    get() = key

                override fun hit(unsharableElement: Int) {
                    println("敲击了${unsharableElement}个人")
                }
            }
        }
        return map[key]!!
    }
}