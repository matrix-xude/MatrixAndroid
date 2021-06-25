package com.xxd.kt.interaction.claaz

import kotlin.reflect.KClass

/**
 *    author : xxd
 *    date   : 2021/6/25
 *    desc   :
 */

fun main() {

    test(JavaClass::class.java)
//    test(JavaClass::class)
    test2(JavaClass::class)
    test2(KtClass::class)
}

fun test(clazz: Class<*>){

}

fun test2(clazz: KClass<*>){

}