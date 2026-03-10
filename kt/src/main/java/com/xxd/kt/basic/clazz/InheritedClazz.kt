package com.xxd.kt.basic.clazz

/**
 *    author : xxd
 *    date   : 2026/3/9
 *    desc   : 类的继承：
 *              1. 如果父类有 primary constructor , 必须在子类中调用父类的 primary constructor
 *              2. 如果父类有只有 secondary constructor, 可以调用父类中随意 secondary constructor
 */
private open class InheritedClazz {

    constructor(i: Int)

    constructor(i: Int, s: String)

}

// 注意： 这里的 InheritedClazz 不能加 ()
private class ClazzA : InheritedClazz {

    // 调用父类的 secondary constructor
    constructor(i: Int) : super(i)

    constructor(i: Int, s: String) : super(i, s)

}


private open class InheritedClazz2(val i: Int) {

    constructor(i: Int, s: String): this(i)
}

private class ClazzB : InheritedClazz2 {

    // 调用父类的 secondary constructor
    constructor(i: Int) : super(i)

    constructor(i: Int, s: String) : super(i,s)

}

