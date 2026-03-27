package com.xxd.kt.coroutines.suspend

import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/**
 *    author : xxd
 *    date   : 2026/3/20
 *    desc   : 
 */
class NoDefaultInterceptor : ContinuationInterceptor {

    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return continuation
    }

}