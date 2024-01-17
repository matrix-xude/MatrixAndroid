package com.xxd.kt.function.inline

import com.xxd.kt.basic.valvar.list
import java.util.Objects

/**
 * author : xxd
 * date   : 2021/7/1
 * desc   : 内联函数在Java、Kotlin中的调用
 */

// 内联函数
inline fun attack(i: Int, block: (Int) -> Int): String {
    return block(i).toString()
}

// 看看oninline的作用，反编译后，看出使用者虽然没有调用该方法，但是生成了block类
inline fun attack1(i: Int, noinline block: (Int) -> Int): String {
    return block(i).toString()
}

// 普通函数
fun attack2(i: Int, block: (Int) -> Int): String {
    println("attack2调用")
    val toString = block(i).toString()
    println("attack2 block调用$toString")
    return toString
}

fun main() {

    // kotlin调用内联函数、普通函数的区别
    attack(1) { i ->
        i + 1
    }
    println("--------------------------")
    attack1(1) {
            i -> i + 1
    }
    println("--------------------------")
    val result = attack2(1) { i ->
        i + 1
        // return 直接写return报错
        return@attack2 13  // 只能return到传入的方法体，返回值必须和方法体一致
    }
    println(result)

    val inlineDemo = InlineDemo()
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    inlineDemo.m1(1) { it * 2 }
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    inlineDemo.m2(2) { it + 5 }

    // 内联函数被调用时可以传入return
    val list = listOf(1, 2, 4, 6)
    foldList(list) {
        return
        it * 5
    }

}

inline fun InlineDemo.m2(i: Int, block: (Int) -> Int): Int {
    return block.invoke(i)
}

inline fun foldList(list: List<Int>, fold: (Int) -> Int): Boolean {
    list.forEach {
        println("当前的数据为$it")
        val invoke = fold.invoke(it)
        if (invoke == 10)
            return true
    }
    return false
}