package com.xxd.design.iterator

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
interface IAggregate<T> {

    fun getIterator():IIterator<T>
}