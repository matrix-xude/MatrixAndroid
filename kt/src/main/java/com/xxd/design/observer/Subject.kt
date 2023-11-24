package com.xxd.design.observer

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
abstract class Subject {

    val observerList = mutableListOf<IObserver>()

    abstract fun notifyObservers()
}