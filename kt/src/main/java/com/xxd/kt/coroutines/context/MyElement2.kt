package com.xxd.kt.coroutines.context

import kotlin.coroutines.CoroutineContext

/**
 * author : xxd
 * date   : 2021/7/7
 * desc   : 验证Key是否可以随便写
 * 结论：可以随便写，只要保证唯一就可以了。
 */
class MyElement2 : CoroutineContext.Element {

    // 单例的key，一个key对应一个Element
    // companion对应一个外部生成
//    companion object MyElementKey : CoroutineContext.Key<MyElement>

    companion object {
       val MyElementKey = object : CoroutineContext.Key<MyElement2>{}

        fun dao1(){
            println("aaa")
        }
    }

    override val key: CoroutineContext.Key<*>
        get() = MyElementKey

}
