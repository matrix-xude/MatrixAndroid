package com.xxd.common.extend

import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

/**
 * author : xxd
 * date   : 2021/8/14
 * desc   :
 */


fun <T : Any> T.deepCopy(): T {

    val kClass = this::class
//    kClass as KClass<T>
    if (!kClass.isData)
        return this
    val primaryConstructor = kClass.primaryConstructor!!

    primaryConstructor.parameters.map {
        val value = (kClass as KClass<T>).memberProperties.first {
            it.name == it.name
        }.get(this)

    }

}