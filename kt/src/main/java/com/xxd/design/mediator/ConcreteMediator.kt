package com.xxd.design.mediator

/**
 *    author : xxd
 *    date   : 2023/11/24
 *    desc   : 具体中介者，需要保存通信人
 */
class ConcreteMediator :IMediator {

    private val list = mutableListOf<AbstractPerson>()

    override fun register(person: AbstractPerson) {
        list.add(person)
    }

    override fun reply(person: AbstractPerson, msg: String) {
        list.forEach {
            it.takeIf { it != person }?.run {
                receiveMsg(msg)
            }
        }
    }
}