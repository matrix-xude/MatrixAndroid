package com.xxd.kt.coroutines.context

import com.xxd.kt.coroutines.basic.log
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

/**
 * author : xxd
 * date   : 2021/7/9
 * desc   : 自定义的拦截器中消息的处理，打印log，原封不动的调用下去
 */
class MyContinuation<T>(private val c : Continuation<T>) : Continuation<T> {
    override val context: CoroutineContext
        get() = c.context

    override fun resumeWith(result: Result<T>) {
        log("MyContinuation拦截器被调用：$result")
        c.resumeWith(result)
    }
}