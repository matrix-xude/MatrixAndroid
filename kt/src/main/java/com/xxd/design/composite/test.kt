package com.xxd.design.composite

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */

fun main() {
    val fold = BranchFold().apply {
        repeat(3) {
            println(it)
            childList.add(0, LeafFold())
        }
    }
    fold.show()
}