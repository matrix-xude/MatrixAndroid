package com.xxd.kt.coroutines.context

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

/**
 *    author : xxd
 *    date   : 2021/7/8
 *    desc   :
 */
class MyContinuation<T>() : Continuation<T> {
    override val context: CoroutineContext
        get() = TODO("Not yet implemented")

    override fun resumeWith(result: Result<T>) {
        TODO("Not yet implemented")
    }
}