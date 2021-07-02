package com.xxd.kt.basic.any

import java.io.IOError
import java.lang.RuntimeException

/**
 *    author : xxd
 *    date   : 2021/7/2
 *    desc   :
 */

/**
 * 分析Any
 * @param any 不可null的Any
 * @param any2 可以null的Any
 * @return 返回值随意
 */
fun any1(any: Any, any2: Any?): Any? {
    return null
}

fun unit1(u: Unit): Unit {}


fun nothing1(): Nothing {
    // 死循环，无法返回，可编译，不报错
    while (true) {
        print(1)
    }
}

fun nothing2(): Nothing {
    // 直接抛异常，可编译，不报错
    throw RuntimeException()
}

// 不能返回，报错
/*fun nothing3(): Nothing {
    return ""
}*/

