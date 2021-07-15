package com.xxd.coroutine.exception

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 *    author : xxd
 *    date   : 2021/7/15
 *    desc   :
 */
object MyScope : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + CoroutineName("MyScope")
}