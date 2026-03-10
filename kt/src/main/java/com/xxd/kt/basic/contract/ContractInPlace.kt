package com.xxd.kt.basic.contract

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 *    author : xxd
 *    date   : 2026/3/4
 *    desc   : 
 */

class ContractInPlace {

    fun initInPlace() {
        val x: Int
        // 因为 apply 声明了 EXACTLY_ONCE
        // 编译器确信 block 会执行，所以允许在里面给 val 赋值
        // 注释该行就是报错，因为内部使用callsInPlace保证了
        val any = Any().apply {
            x = 10
        }
        println(x) // 编译通过，因为契约保证 x 一定被初始化了
    }
}

@OptIn(ExperimentalContracts::class)
fun ContractInPlace.m1(block: ContractInPlace.() -> Unit) {
    contract {
        // 契约：block 必须执行
        callsInPlace(block, kotlin.contracts.InvocationKind.EXACTLY_ONCE)
    }
    // 契约只约束在 m1 内部对 block 的调用行为。你在外面怎么调 f1，编译器管不着。 (其实内部调用2次也只会警告，不会报错)
    // 反编译后不存在任何约束
    block()
}