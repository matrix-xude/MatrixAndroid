package com.xxd.design.iterator

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */

fun main() {
    val iterator = IntArrayAggregate().getIterator()
    while (iterator.hasNext()){
        val next = iterator.getNext()
        println("当前获取到的数据：$next")
    }
}