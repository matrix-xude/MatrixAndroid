package com.xxd.myself

import kotlin.reflect.KProperty

/**
 *    author : xxd
 *    date   : 2021/6/22
 *    desc   :
 */
class AAA {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

fun main() {
    val b = B()
    println(b.str)
    b.str = "111"

    val str by lazy {
        ""
    }
}

class B{
    var str : String by AAA()

}
