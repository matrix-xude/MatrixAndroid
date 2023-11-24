package com.xxd.design.command

/**
 *    author : xxd
 *    date   : 2023/11/24
 *    desc   :
 */

fun main() {
    Invoker().apply {
        commandList.apply {
            add(Command1())
            add(Command2())
        }
    }.execute()
}