package com.xxd.kt.basic.alias

/**
 *    author : xxd
 *    date   : 2026/3/6
 *    desc   : 别名 alise
 */
typealias Books = List<String>

typealias fun1 = (Int, Int) -> String
typealias fun2 = Int.(Int) -> String

class AliasTest {


    fun m1() {
        val books: Books = ArrayList()
    }

    fun m2(fun1: fun1){

    }

    fun m3(fun2: fun2){

    }


    fun invoke(){
        // 两种都可以调用，证明 A,B -> C 与 A.(B) -> C 等价
        val f1 =  { a: Int, b: Int -> "$a$b" }
        m2(f1)
        m3(f1)

        // 显式指定类型为 Int.(Int) -> String
        val f2: Int.(Int) -> String = { b -> "$this$b" }
        m2(f2)
        m3(f2)

    }
}