package com.xxd.view.recycler

/**
 *    author : xxd
 *    date   : 2021/7/14
 *    desc   :
 */
data class Bean1(val id: Int, val bean2: Bean2) {

    data class Bean2(var name: String)
}
