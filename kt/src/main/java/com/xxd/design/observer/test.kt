package com.xxd.design.observer

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */

fun main() {
    val subject = ConcreteSubject().apply {
        observerList.add(ObserverA())
        observerList.add(ObserverB())
    }
    subject.notifyObservers()
}