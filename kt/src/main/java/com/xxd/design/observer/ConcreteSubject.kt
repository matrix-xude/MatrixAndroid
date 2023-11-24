package com.xxd.design.observer

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
class ConcreteSubject : Subject() {

    override fun notifyObservers() {
        observerList.forEach {
            it.response()
        }
    }
}