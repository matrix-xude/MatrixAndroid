package com.xxd.kt.tips

/**
 *    author : xxd
 *    date   : 2023/12/28
 *    desc   : 不带val的构造函数参数，都是在构造函数里赋值，顺序是从上到下
 */
class InitOrderDemo(name: String) {
    //    val firstProperty = "First property: $name".also(::println)
    val firstProperty = "First property: $name".also {
        println(it)
    }

    init {
        println("First initializer block that prints $name")
    }

    val secondProperty = "Second property: ${name.length}".also(::println)

    init {
        println("Second initializer block that prints ${name.length}")
    }
}
//sampleEnd

fun main() {
    InitOrderDemo("hello")
}