package com.xxd.design.mediator

/**
 *    author : xxd
 *    date   : 2023/11/24
 *    desc   : 抽象通信人，用来保存中介者，同性
 */
abstract class AbstractPerson {

    var mediator : IMediator? = null

    abstract fun sendMsg(string: String)

    abstract fun receiveMsg(string: String)
}