package com.xxd.kt.delegate.attribute

import kotlin.reflect.KProperty

/**
 *    author : xxd
 *    date   : 2021/7/2
 *    desc   : 通过实现接口的方式来实现接口委托
 */
class SecondDelegate {

    private var getValueAmount = 0
    var setValueAmount = 0

    // operator 关键字就可以告诉编译器这是覆写了某个操作，不再需要继承接口
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        val str = "第${++getValueAmount}次读取该属性数值"
        println("对象：${thisRef?.javaClass?.simpleName} 中的 ${property.name} 获取了一次值，返回值：${str}")
        return str
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        val str = "第${++setValueAmount}次赋值->$value"
        println("对象：${thisRef?.javaClass?.simpleName} 中的 ${property.name} 赋值一次：${str}")
    }
}

// 通过扩展函数实现也可以，优先使用内部函数，没有内部函数再使用此函数
// operator 的好处，可以在扩展函数处理覆写操作
operator fun  SecondDelegate.setValue(thisRef: Any?, property: KProperty<*>, value: String) {
    val str = "扩展函数${setValueAmount}"
    println("对象：${thisRef?.javaClass?.simpleName} 中的 ${property.name} 赋值一次：${str}")
}