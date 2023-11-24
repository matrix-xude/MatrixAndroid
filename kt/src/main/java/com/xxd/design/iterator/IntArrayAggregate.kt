package com.xxd.design.iterator

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
class IntArrayAggregate : IAggregate<Int> {

    private val arr = intArrayOf(1,3,5,7)

    override fun getIterator(): IIterator<Int> {
        return IntArrayIterator(arr)
    }
}