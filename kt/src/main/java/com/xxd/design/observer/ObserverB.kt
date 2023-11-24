package com.xxd.design.observer

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
class ObserverB : IObserver {
    override fun response() {
        println("观察者B监听到了事件")
    }
}