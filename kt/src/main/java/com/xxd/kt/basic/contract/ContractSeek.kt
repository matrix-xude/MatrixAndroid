package com.xxd.kt.basic.contract

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 *    author : xxd
 *    date   : 2026/3/4
 *    desc   :  探寻 contract {} 的作用
 *              1. 表示该与编译器的“君子约定”，表示我一定做了某事（如：判断非null），编译器认为已经非null了，但不做实际检查
 */

fun isNotNull(s: String?): Boolean {
    return s != null
}

/*fun test(s: String?) {
    // 使用外部方法判断是否非空，内部无法知道已经不为null了，无法使用s.length
    if (isNotNull(s)) {
        // 报错！编译器不确定 s 在这里是否非空
        println(s.length)
    }
}*/


@OptIn(ExperimentalContracts::class)
fun isNotNull2(s: String?): Boolean {
    contract {
        // 契约：如果返回值为 true，暗示 s 满足 String 类型（非 null）
        returns(true) implies (s != null)
    }
    return s != null
}

fun test2(s: String?) {
    // 使用外部方法内有contract契约
    if (isNotNull2(s)) {
        // 正常！编译器确定 s 在这里非空
        println(s.length)
    }
}

fun testInPlace() {
    val contractInPlace = ContractInPlace()
    val f1 = { _:ContractInPlace ->
        println("hello 三战")
    }
    contractInPlace.m1(f1)
    // 契约只约束在 m1 内部对 block 的调用行为。你在外面怎么调 f1，编译器管不着。
    f1.invoke(contractInPlace)
}

fun main() {
    testInPlace()
}

