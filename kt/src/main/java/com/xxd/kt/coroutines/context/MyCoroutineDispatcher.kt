package com.xxd.kt.coroutines.context

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

/**
 * author : xxd
 * date   : 2021/7/9
 * desc   :
 */
class MyCoroutineDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        TODO("Not yet implemented")
    }
}