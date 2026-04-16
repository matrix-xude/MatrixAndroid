package com.xxd.kt.coroutines.suspend

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 *    author : xxd
 *    date   : 2026/4/14
 *    desc   : 深入探查Continuation的调用链
 *              结论：1. 普通suspend方法，内部调用了多个suspend方法，才会生成自己的Continuation
 *                   2. 类似suspendCancellableCoroutine这种特殊的suspend方法，会按照需求，内部生成多个Continuation
 */


suspend fun mc1() = suspendCancellableCoroutine { cont ->
    cont.resume(1)
}

suspend fun mc2() {
    // 因为调用了2个suspend方法，所以mc2会生成自己的Continuation
    delay(10)
    mc1()
}

suspend fun mc3(){
    // 只调用了1个suspend方法，所以mc3不会生成自己的Continuation，只是透传了上一个suspend方法的Continuation
    delay(1)
}


fun main() {
    runBlocking {
        mc3()
    }
}