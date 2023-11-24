package com.xxd.design.composite

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */
class BranchFold : IFold {

    val childList = mutableListOf<IFold>()

    override fun isFolder(): Boolean {
        return true
    }

    override fun subFolds(): List<IFold> {
        return childList
    }

    override fun readIt(): String {
        throw NoSuchMethodException("树枝节点不能调用此法")
    }

    override fun show() {
        subFolds().forEach { it.show() }
    }
}