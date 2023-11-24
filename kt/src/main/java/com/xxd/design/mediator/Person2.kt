package com.xxd.design.mediator

/**
 *    author : xxd
 *    date   : 2023/11/24
 *    desc   :
 */
class Person2 : AbstractPerson() {

    override fun sendMsg(string: String) {
        mediator?.reply(this, string)
    }

    override fun receiveMsg(string: String) {
        println("第2人收到了消息：$string")
    }
}