package com.xxd.kt.delegate.attribute

/**
 *    author : xxd
 *    date   : 2023/12/29
 *    desc   : 属性委托的第一个参数是调用的类
 */
class Ref {
    var d1 : Int by FirstDelegate()

    fun use(){
        println("user -> $d1")
    }
}