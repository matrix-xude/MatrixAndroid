package com.xxd.kt.function.inherit

/**
 *    author : xxd
 *    date   : 2026/3/8
 *    desc   : 
 */
internal open class FunFather {

    // 增加@JvmOverloads 后，反编译可以看到多了个方法
    @JvmOverloads
    open fun m1(i: Int = 5, j: Int = 10): Int{
        return i -j;
    }
}