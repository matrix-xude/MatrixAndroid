package com.xxd.kt.reflect

import kotlin.reflect.KCallable
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0

/**
 *    author : xxd
 *    date   : 2026/3/10
 *    desc   : kotlin返回调用方法、属性、构造函数
 *              基类是 KCallable<out R>
 *
 *           Callable Reference（可调用引用）。
 *           它的核心作用是将代码逻辑（函数）或状态声明（属性）转换为一等公民对象，让你能够像传递变量一样传递它们。
 */
class CallableTest {

     var pair: Pair<Int, String> = Pair(1, "abc")


    fun m1() {

        // KProperty0 ，已绑定的属性
        println(::pair.get())

        // 提取出的k是KCallable<Pair<Int, String>> 的子类 KProperty<Pair<Int, String>> 所有属性的父类, KProperty0 才有具体信息
        val k : KProperty<Pair<Int, String>> = this::pair
        println("name=${k.name}, isFinal=${k.isFinal}, isLateinit=${k.isLateinit}, value=${k.call()}")
    }


    fun m2() {
        // 提取出的k是KCallable<Int> 的子类 KFunction<Int> , 本质是合成类 KFunction1<CallableTest,Int>，找不到源码
        val k :(CallableTest) -> Int = this::m3
        m4(k)
        // 下面3个等价， ::Callable Reference（可调用引用）
        // val functionReference: (CallableTest) -> Int = this::m3
        m4(::m3)
        m4(this::m3)
        m4 { nullSafety ->
            m3(nullSafety)
        }
    }

    fun m3(test: CallableTest): Int {
        return 1
    }

    fun m4(test: (CallableTest) -> Int) {
    }
}



fun main() {
    val test = CallableTest()
    test.m1()
    test.m2()
}