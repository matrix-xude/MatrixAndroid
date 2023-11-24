package com.xxd.design.chain

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */
class SecondHandler : Handler() {

    override fun disposeRequest(i: Int) {
        if (i > 15){
            println("第2链检测到了大于15的数字")
        }
        nextHandler?.disposeRequest(i)
    }
}