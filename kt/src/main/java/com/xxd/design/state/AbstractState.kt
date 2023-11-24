package com.xxd.design.state

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
abstract class AbstractState constructor(val context: StateContext) {

    abstract fun handle()
}