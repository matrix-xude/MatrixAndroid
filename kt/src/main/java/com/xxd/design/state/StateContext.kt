package com.xxd.design.state

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
class StateContext {

    var state : AbstractState = StateA(this)

    fun click(){
        state.handle()
    }
}