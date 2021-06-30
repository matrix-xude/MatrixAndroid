package com.xxd.kt.function

/**
 * author : xxd
 * date   : 2021/6/25
 * desc   :
 */

inline fun show(block: (str: String) -> Int) {
    block("123")
}

inline fun show2(block: () -> Unit) {
    block()
}

inline fun show3(block: (i: Int, j: Int) -> Int) {
    block(1, 2)
}


fun main() {
    // 使用原始方式
    show(fun(str: String): Int {
        return str.toInt()
    })

    // 使用lambda，同时去掉了外部()
    show { str ->
        str.toInt()
    }

    // 使用lambda,只有一个参数可以去掉参数
    show {
        it.toInt()
    }

    show2 { ->
        println("氧气")
    }

    show3 { i, j ->
        i + j
    }

    show3 { i, j ->
        i + j
    }

    show3(fun(i: Int, j: Int): Int {
        return i + j
    })
}