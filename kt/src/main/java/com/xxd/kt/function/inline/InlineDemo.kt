package com.xxd.kt.function.inline

/**
 *    author : xxd
 *    date   : 2023/12/29
 *    desc   :
 */
class InlineDemo {

    val i : Int by lazy { 1 }

    fun m1(i: Int, block: (Int) -> Int): Int {
        return block.invoke(i)
    }

}