package com.xxd.kt.coroutines.context

import com.xxd.kt.coroutines.basic.log
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/**
 * author : xxd
 * date   : 2021/7/8
 * desc   : 自己的拦截器，代替系统的调度器
 */
class MyInterceptor : ContinuationInterceptor {
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        log("拦截器MyInterceptor 包裹了 Continuation: $continuation")
        return MyContinuation(continuation)
    }
}