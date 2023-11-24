package com.xxd.design.bridge

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */
class MyBridge(fruit: IFruit) : BridgeFruit(fruit) {
    override fun getColor(): String {
        return "绿色"
    }
}