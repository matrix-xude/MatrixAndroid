package com.xxd.design.momento

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
class Originator {

    var score = 0  // 分数，需要保存的状态

    fun storeMemento(): Memento {
        return Memento(score)
    }

    fun restoreMemento(memento: Memento) {
        score = memento.score
    }
}