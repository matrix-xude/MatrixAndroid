package com.xxd.design.state

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
class StateB constructor(context: StateContext) : AbstractState(context) {
    override fun handle() {
        println("当前是状态B,立马切换成状态A")
        context.state = StateA(context)
    }
}