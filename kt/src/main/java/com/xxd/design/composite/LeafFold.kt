package com.xxd.design.composite

import kotlin.random.Random

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */
class LeafFold : IFold {

    override fun isFolder(): Boolean {
        return false
    }

    override fun subFolds(): List<IFold> {
        throw NoSuchMethodException("叶子节点不能调用此法")
    }

    override fun readIt(): String {
        return "我是子节点${Random.nextInt(10)}\n"
    }

    override fun show() {
        println(readIt())
    }
}