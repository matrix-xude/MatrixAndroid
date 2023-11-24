package com.xxd.design.command

/**
 *    author : xxd
 *    date   : 2023/11/24
 *    desc   :
 */
class Command2 : ICommand {

    private val receiver = Receiver2()

    override fun execute() {
        receiver.action()
    }
}