package com.xxd.design.state

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   :
 */
class StateA constructor(context: StateContext) : AbstractState(context) {
    override fun handle() {
        println("当前是状态A,立马切换成状态B")
        context.state = StateB(context)
    }
}