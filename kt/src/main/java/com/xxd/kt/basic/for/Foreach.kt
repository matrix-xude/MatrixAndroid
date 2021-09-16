package com.xxd.kt.basic.`for`

import com.xxd.kt.coroutines.basic.log

/**
 *    author : xxd
 *    date   : 2021/9/8
 *    desc   :
 */

fun main() {
    m3()
}

private fun m1() {
    val list = listOf(1, 3, 5, 2, 7)
    list.forEach {
        if (it == 3)
            return@forEach
        println("$it")
    }
}

private fun m2(){
    val list = mutableListOf(1,2,4,5,6)
    val subList = list.subList(2,list.size)
    subList.clear()
    subList.add(11)
    log(list)
    log(subList)
}

private fun m3(){
    for (index in 1 until 1){
        println("$index")
    }
}