package com.xxd.kt.data.base

/**
 *    author : xxd
 *    date   : 2021/10/16
 *    desc   : 打印data类
 */


fun main() {
    m4()
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
    val copy = d1.copy(inner = d1.inner.copy(), list = copyList) // 改变id的copy
//    println(d1)
//    println(copy)
    println("${d1 === copy}") // 本身是否同一个对象，结论：不是
    println("${d1.inner === copy.inner}")
    println("${d1.list === copy.list}")
    println("${d1.list!![0].list === copy.list!![0].list}")
}

data class D1<T>(
    val id: Int,
    val name: String,
    val inner: D3,
    val list: List<T>?,
)

data class D2(
    val price: Double,
    val desc: String,
    val list: List<D3>?
)

data class D3(
    val boss: String,
)

class D4(
    val boss: String,
)