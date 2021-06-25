package com.xxd.kt.interaction.claaz

/**
 *    author : xxd
 *    date   : 2021/6/25
 *    desc   :
 */
class KtClass {

    companion object{
        val clazz = KtClass::class // kotlin类的class
        val clazz2 = KtClass::class.java // kotlin类对应的java class
        val clazz3 = JavaClass::class // java类的class
        val clazz4 = JavaClass::class.java // java类对应的java class
    }
}