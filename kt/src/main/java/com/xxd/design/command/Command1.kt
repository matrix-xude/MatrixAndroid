package com.xxd.design.command

/**
 *    author : xxd
 *    date   : 2023/11/24
 *    desc   :
 */
class Command1 : ICommand {

    private val receiver = Receiver1()

    override fun execute() {
        receiver.action()
    }
}