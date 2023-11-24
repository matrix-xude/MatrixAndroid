package com.xxd.design.iterator

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
interface IIterator<T> {

    fun hasNext(): Boolean

    fun getNext(): T
}