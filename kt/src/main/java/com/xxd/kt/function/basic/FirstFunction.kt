package com.xxd.kt.function.basic

/**
 *    author : xxd
 *    date   : 2021/6/29
 *    desc   : 函数的基础
 *    1.与其它一级公民类似，函数的作用范围很大
 *    2.自动推断返回值类型
 *    3.lambda表示式赋值函数
 */

// 函数作为顶部变量
var function1: ((i: Int) -> String)? = null

// 函数作为参数
var function2: (((Int) -> String) -> Unit)? = null

// 函数作为返回值
var function3: (() -> ((Int) -> String))? = null

/**
 * 自动推断返回值类型可以去掉{}
 * 简单类型很容易看出来，但是复杂的情况不容易一眼看出
 */
fun add(i: Int, j: Int) = i + j

// 接收一个函数作为参数
fun addFunction(num1: Int, num2: Int, block: (Int, Int) -> Int) {
    val num3 = block(num1, num2)
    println("我是调用了传入函数 $block 计算了2个值 : $num3")
}

// 测试函数的完整调用
fun invokeAddFunction() {
    // 1.完整调用
    addFunction(1, 2, fun(i: Int, j: Int): Int {
        return i + j
    })

    // 2.完整调用+省略参数类型
    addFunction(1, 2, fun(i, j): Int {
        return i * j
    })

    // 3.完整调用+省略参数类型+自动推断返回值
    addFunction(1, 2, fun(i, j) = i + j)

    // 4.lambda表达式调用
    /*addFunction(1, 2, { i: Int, j: Int ->
        i * j
    })*/

    // 5.lambda表达式调用+省略参数类型
    /*addFunction(1, 2, { i, j ->
        i + j
    })*/

    // 6.lambda表达式调用+省略参数类型+最后一个参数为lambda可以写在()外部
    addFunction(1, 2) { i, j ->
        i * j
    }

    // 7.使用方法引用作为函数
    addFunction(1, 2, ::add)
}


fun main() {
    invokeAddFunction()
}


