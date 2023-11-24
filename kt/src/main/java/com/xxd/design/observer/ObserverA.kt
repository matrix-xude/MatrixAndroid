package com.xxd.design.observer

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
class ObserverA : IObserver {
    override fun response() {
        println("观察者A监听到了事件")
    }
}