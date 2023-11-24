package com.xxd.design.command

/**
 *    author : xxd
 *    date   : 2023/11/24
 *    desc   :
 */
class Invoker {

    val commandList = mutableListOf<ICommand>()

    fun execute(){
        commandList.forEach {
            it.execute()
        }
    }
}