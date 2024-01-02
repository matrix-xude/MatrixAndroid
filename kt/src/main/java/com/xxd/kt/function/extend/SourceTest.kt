package com.xxd.kt.function.extend

/**
 *    author : xxd
 *    date   : 2023/12/29
 *    desc   : 带接受者的函数，与不带接受者的函数都可以被调用
 */
class SourceTest {

    private lateinit var fun1: Source.(Int, Int) -> Int
    private lateinit var fun2: (Source, Int, Int) -> Int


    fun m1(){
        // 带接受者的函数，与不带接受者的函数都可以被调用
        useLambda(fun1)
        useLambda(fun2)
    }

    fun useLambda(calculater: (Source, Int, Int) -> Int) {
        calculater.invoke(Source(),1,3)
    }
}