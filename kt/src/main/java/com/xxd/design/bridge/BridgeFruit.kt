package com.xxd.design.bridge

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   : 桥接类，加一个维度：颜色
 */
abstract class BridgeFruit(private val fruit: IFruit) {

    abstract fun getColor(): String

    fun show() {
        println("我是一个${getColor()}的${fruit.name()}")
    }
}