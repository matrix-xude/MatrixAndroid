package com.xxd.design.momento

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */

fun main() {
    val caretaker = Caretaker()

    val originator = Originator().apply {
        score = 84
        score = 13
        caretaker.list.add(storeMemento())
        score = 99
    }

    println("当前分数：${originator.score}")
    originator.restoreMemento(caretaker.list.removeLast())
    println("当前分数：${originator.score}")
}