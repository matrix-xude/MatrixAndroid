package com.xxd.kt.coroutines.exception

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 *    author : xxd
 *    date   : 2024/1/19
 *    desc   : 自定义的作用域
 */
object MyScope : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + CoroutineName("MyScope")
}