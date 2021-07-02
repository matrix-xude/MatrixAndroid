package com.xxd.kt.delegate.attribute

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *    author : xxd
 *    date   : 2021/7/1
 *    desc   : 一个简单的委托属性例子
 */
class FirstDelegate : ReadWriteProperty<Any?, Int>{

    private var invokeGetAmount = 0
    private var invokeSetAmount = 0

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        invokeGetAmount++
        println("对象：'${thisRef?.javaClass?.simpleName}'中的'${property.name}'获取了一次值，返回数值：${invokeGetAmount}")
        return invokeGetAmount
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        invokeSetAmount++
        println("对象：'${thisRef?.javaClass?.simpleName}'中的'${property.name}'赋值一次${value},调用次数${invokeSetAmount}")
    }
}