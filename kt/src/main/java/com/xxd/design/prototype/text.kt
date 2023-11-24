package com.xxd.design.prototype


/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */

fun main() {
    shallowCopy()
    deepCopy()
}

fun shallowCopy(){
    val prototypeImpl = PrototypeImpl()
    val clone = prototypeImpl.clone()
    prototypeImpl.mList[0] = 0
    prototypeImpl.mInt = 0
    /**
     * 原类:mList=[0, 2, 3, 4],mInt=0
     * clone类:mList=[0, 2, 3, 4],mInt=1
     * clone类的第一个元素变为0，随着原类改变
     */
    println("原类:mList=${prototypeImpl.mList},mInt=${prototypeImpl.mInt}")
    println("clone类:mList=${clone.mList},mInt=${clone.mInt}")
}

fun deepCopy(){
    val prototypeImpl = PrototypeImpl2()
    val clone = prototypeImpl.clone()
    prototypeImpl.mList[0] = 0
    prototypeImpl.mInt = 0
    /**
     * 原类:mList=[0, 2, 3, 4],mInt=0
     * clone类:mList=[1, 2, 3, 4],mInt=1
     * clone类的第一个元素，还是1，不会随着原类改变
     */
    println("原类:mList=${prototypeImpl.mList},mInt=${prototypeImpl.mInt}")
    println("clone类:mList=${clone.mList},mInt=${clone.mInt}")
}