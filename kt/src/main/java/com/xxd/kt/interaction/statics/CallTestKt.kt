package com.xxd.kt.interaction.statics

/**
 *    author : xxd
 *    date   : 2021/6/25
 *    desc   : kotlin调用static相关的吃
 */

fun main() {
    callKtObject()
    callKtCompanion()
    callJavaStatic()
}

fun callKtObject() {
    println("KtObject: ${KtObject.i}, ${KtObject.str}")
    KtObject.show()
    KtObject.show2()
}

fun callKtCompanion() {
    println("KtCompanion: ${KtCompanion.i}, ${KtCompanion.str}")
    KtCompanion.show()
    KtCompanion.show2()

    /*无法调用，必须创建对象再调用
    KtCompanion.show3()*/
}

fun callJavaStatic(){
    println("KtCompanion: ${JavaStatic.i}, ${JavaStatic.str}")
    JavaStatic.show()
}