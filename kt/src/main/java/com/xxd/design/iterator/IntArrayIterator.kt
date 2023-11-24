package com.xxd.design.iterator

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
class IntArrayIterator(private val intArray: IntArray) : IIterator<Int> {

    private var index = 0

    override fun hasNext(): Boolean {
        return index < intArray.size
    }

    override fun getNext(): Int {
        return intArray[index++]
    }
}