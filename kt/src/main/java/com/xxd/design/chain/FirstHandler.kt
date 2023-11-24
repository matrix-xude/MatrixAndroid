package com.xxd.design.chain

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */
class FirstHandler : Handler() {

    override fun disposeRequest(i: Int) {
        var j = i
        while (j < 10){
            println("第一链处理了该数字$j,并且加10")
            j+=10
        }
        nextHandler?.disposeRequest(j)
    }
}