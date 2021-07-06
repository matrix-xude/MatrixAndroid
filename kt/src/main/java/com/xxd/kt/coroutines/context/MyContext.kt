package com.xxd.kt.coroutines.context

import kotlin.coroutines.CoroutineContext

/**
 * author : xxd
 * date   : 2021/7/7
 * desc   : 看看上下文接口怎么实现
 */
class MyContext : CoroutineContext{

    // 提供了一个初始类initial R，一个函数operation，需要你返回一个初始类型一样的R
    // 猜测：这里需要提供就是 CoroutineContext.Element，应该是由自己的环境决定是返回原始R,还是经过operation进行变换后的R
    override fun <R> fold(initial: R, operation: (R, CoroutineContext.Element) -> R): R {
        TODO("Not yet implemented")
    }

    // 猜测：key类型是null接口，应该是用来标记Element的类型，key可能是不同泛型E的单例
    // 一种key代表一种Element,这里决定当前上下文是否包含某种key的对应的Element
    override fun <E : CoroutineContext.Element> get(key: CoroutineContext.Key<E>): E? {
        TODO("Not yet implemented")
    }

    // 猜测：当前上下文中减去某种key对应的Element,移除后返回自己
    override fun minusKey(key: CoroutineContext.Key<*>): CoroutineContext {
        TODO("Not yet implemented")
    }

}
