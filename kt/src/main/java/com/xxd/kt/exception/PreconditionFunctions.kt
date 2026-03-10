package com.xxd.kt.exception

/**
 *    author : xxd
 *    date   : 2026/3/7
 *    desc   : kotlin提供了3种使用预检查函数来自动抛出异常的其他方法
 *              1. require , Checks user input validity , IllegalArgumentException
 *              2. check , Checks object or variable state validity , IllegalStateException
 *              3. error , Indicates an illegal state or condition , IllegalStateException
 *            这3中方法抛出异常，并不是逻辑区分，而是看表达的异常类型不同
 *              1 是传入参数不合法
 *              2 是内部状态不合法
 *              3 是代码执行到不应该到的地方
 *
 */

fun main() {
//    m1(-1)
//    m2("1")
    m3("monkey")
}


fun m1(count: Int): List<Int> {
    require(count >= 0) { "Count must be non-negative. You set count to $count." }
    return List(count) { it + 1 }
}

fun m2(s: String) {
    check(s.isNotEmpty()) { "String must not be empty." }
}

fun m3(animal: String) {
    when (animal) {
        "dog" -> println("dog")
        "cat" -> println("cat")
        else -> error("Unknown animal: $animal")
    }
}
