package com.xxd.kt.coroutines.context

import kotlin.coroutines.CoroutineContext

/**
 * author : xxd
 * date   : 2021/7/7
 * desc   :
 */
class MyElement : CoroutineContext.Element {

    // 单例的key，一个key对应一个Element
    // companion对应一个外部生成
    companion object MyElementKey : CoroutineContext.Key<MyElement>

    override val key: CoroutineContext.Key<*>
        get() = MyElementKey

}
