package com.xxd.kt.data.base

import com.fasterxml.jackson.databind.JsonNode
import com.xxd.kt.data.copy.ArrayModificationMode
import com.xxd.kt.data.copy.deepCopy
import com.xxd.kt.data.copy.mapper

/**
 *    author : xxd
 *    date   : 2021/10/16
 *    desc   : 打印data类
 */


fun main() {
    m6()
    m7()
}

// 测试打印，内部包含list
private fun m1() {
    val d3List1 = listOf(D3("深渊领主"), D3("六翼天使"), D3("八岐大蛇"))
    val d3List2 = listOf(D3("放射物"), D3("天地大冲撞"), D3("热力学第二定律"))

    val d21 = D2(123356.33, "微不足道的boss", d3List1)
    val d22 = D2(9999999999.99, "终极boss", d3List2)

    val innerD3 = D3("内部boss,哈哈")

    val d1 = D1(233, "毁灭的", innerD3, listOf(d21, d22))

    println(d1.toString())
}

// 测试data类型与普通class类型打印的区别
private fun m2() {
    val d3 = D3("食尸鬼")  // data类型
    val d4 = D4("东京食尸鬼") // 普通class
    println(d3.toString())
    println(d4.toString())
}

// 测试data的copy方法
private fun m3() {
    val d3List1 = listOf(D3("深渊领主"), D3("六翼天使"), D3("八岐大蛇"))
    val d3List2 = listOf(D3("放射物"), D3("天地大冲撞"), D3("热力学第二定律"))

    val d21 = D2(123356.33, "微不足道的boss", d3List1)
    val d22 = D2(9999999999.99, "终极boss", d3List2)

    val innerD3 = D3("内部boss,哈哈")

    val d1 = D1(233, "毁灭的", innerD3, listOf(d21, d22))

    val copy = d1.copy(id = 2) // 改变id的copy
    println(d1)
    println(copy)
    println("${d1 === copy}") // 本身是否同一个对象，结论：不是
    println("${d1.inner === copy.inner}") // copy后的内部obj是否同一个对象，结论：是
    println("${d1.list === copy.list}") // copy后的内部list是否同一个对象，结论：是
}

// 测试data的copy方法2
private fun m4() {
    val d3List1 = listOf(D3("深渊领主"), D3("六翼天使"), D3("八岐大蛇"))
    val d3List2 = listOf(D3("放射物"), D3("天地大冲撞"), D3("热力学第二定律"))

    val d21 = D2(123356.33, "微不足道的boss", d3List1)
    val d22 = D2(9999999999.99, "终极boss", d3List2)

    val innerD3 = D3("内部boss,哈哈")

    val d1 = D1(233, "毁灭的", innerD3, listOf(d21, d22))

    val copyList = mutableListOf<D2>()
    d1.list?.forEach {
        copyList.add(it.copy())
    }
    val copy = d1.copy(inner = d1.inner?.copy(), list = copyList) // 改变id的copy
//    println(d1)
//    println(copy)
    println("${d1 === copy}") // 本身是否同一个对象，结论：不是
    println("${d1.inner === copy.inner}")
    println("${d1.list === copy.list}")
    println("${d1.list!![0].list === copy.list!![0].list}")
}

// 测试data的deepCopy
private fun m5() {
    val d3List1 = listOf(D3("深渊领主"), D3("六翼天使"), D3("八岐大蛇"))
    val d3List2 = listOf(D3("放射物"), D3("天地大冲撞"), D3("热力学第二定律"))

    val d21 = D2(123356.33, "微不足道的boss", d3List1)
    val d22 = D2(9999999999.99, "终极boss", d3List2)

    val innerD3 = D3("内部boss,哈哈")

    val d1 = D1(233, "毁灭的", innerD3, listOf(d21, d22))

    val copyList = mutableListOf<D2>()
    d1.list?.forEach {
        copyList.add(it.copy())
    }

    val d2 = d1.copy()
    val time1 = System.currentTimeMillis()
    val deepCopy = d1.deepCopy()
    val time2 = System.currentTimeMillis()
    println("深拷贝用时： ${time2 - time1}")
//    val copy = d1.copy(inner = d1.inner.copy(), list = copyList) // 改变id的copy
//    println(d1)
//    println(copy)
    println("${d1 === deepCopy}") // 本身是否同一个对象，结论：不是
    println("${d1.inner === deepCopy.inner}")
    println("${d1.list === deepCopy.list}")
    println("${d1.list!![0].list === deepCopy.list!![0].list}")

    println("${d1 === d2}") // 本身是否同一个对象，结论：不是
    println("${d1.inner === d2.inner}")
    println("${d1.list === d2.list}")
    println("${d1.list!![0].list === d2.list!![0].list}")
    println(d1)
    println(deepCopy)

}

// 测试10000个数据的集合拷贝时间；结论：第一次拷贝没有缓存对象的解析，需要500mm，后面只需要44mm
private fun m6() {
    val d1 = D1(1, "100次循环", null, getD2List(100))
    val d12 =  D1 (1, "10000次循环", null, getD2List(10000))

    val time3 = System.currentTimeMillis()
    val deepCopy2 = d12.deepCopy("id", null)
    val time4 = System.currentTimeMillis()
    println("深拷贝d12用时： ${time4 - time3}")

    val time1 = System.currentTimeMillis()
    val deepCopy = d1.deepCopy("id", null)
    val time2 = System.currentTimeMillis()
    println("深拷贝d1用时： ${time2 - time1}")

}

// 测试有缓存的100个，10000个集合的拷贝，分别是1mm,44mm
private fun m7() {
    val d1 = D1(1, "100次循环", null, getD2List(100))
    val d12 =  D1 (1, "100次循环", null, getD2List(10000))

    val time3 = System.currentTimeMillis()
    val deepCopy2 = d12.deepCopy("id", null)
    val time4 = System.currentTimeMillis()
    println("深拷贝d12用时： ${time4 - time3}")

    val time1 = System.currentTimeMillis()
    val deepCopy = d1.deepCopy("id", null)
    val time2 = System.currentTimeMillis()
    println("深拷贝d1用时： ${time2 - time1}")

}

private fun getD2List(number: Int): MutableList<D2> {
    val mutableListOf = mutableListOf<D2>()
    repeat(number) {
        mutableListOf.add(D2())
    }
    return mutableListOf
}


data class D1(
    val id: Int,
    val name: String,
    val inner: D3?,
    val list: List<D2>?,
) {
    constructor() : this(0, "", null, mutableListOf())
}

data class D2(
    val price: Double,
    val desc: String,
    val list: List<D3>?
) {
    constructor() : this(0.0, "", mutableListOf())
}

data class D3(
    val boss: String,
) {
    constructor() : this("")
}

class D4(
    val boss: String,
)