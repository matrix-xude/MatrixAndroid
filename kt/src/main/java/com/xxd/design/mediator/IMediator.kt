package com.xxd.design.mediator

/**
 *    author : xxd
 *    date   : 2023/11/24
 *    desc   : 抽象中介者，用来注册通信人
 */
interface IMediator {

    fun register(person: AbstractPerson)

    fun reply(person: AbstractPerson, msg: String)
}