package com.xxd.kt.function.extend

/**
 *    author : xxd
 *    date   : 2026/3/9
 *    desc   : 隐式扩展函数，防止污染整个namespace
 */
class AnonymousFunction {

    fun m1(){
        val aa = AA(22, "port")
        // 隐式扩展函数，AA后面不需要接函数名
        val rateFun = fun AA.(rate: Double) = this.i * rate
        println(aa.rateFun(2.5))
    }

    fun m2(){
        val aa = AA(22, "port")
        // 隐式扩展函数的作用域限制在扩展的方法中
//        aa.rateFun(2.5)
    }
}

data class AA(val i : Int, val name : String)

// 扩展属性
val AA.email : String
    get() = "$i@$name"

fun main() {
    AnonymousFunction().m1()
    println(AA(22, "port").email)

}