package com.xxd.kt.basic.inherit

/**
 *    author : xxd
 *    date   : 2021/8/4
 *    desc   :
 */
class BB( val i : Int, val j : Int) : AA(i) {

    init {
        println("子类的init被调用")
    }

    fun add(i : Int):Int{
        return i+1
    }
}