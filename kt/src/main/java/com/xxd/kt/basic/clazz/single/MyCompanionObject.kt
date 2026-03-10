package com.xxd.kt.basic.clazz.single

/**
 *    author : xxd
 *    date   : 2026/3/10
 *    desc   : 
 */
class MyCompanionObject {

    // 就是普通成员变量
    val runtimeTag = "RUNTIME_VARIABLE"

    init {
        println("普通类 MyCompanionObject 被加载并初始化了！ (init 块执行)")
    }

    companion object Factory {
        // 懒加载
        val instance by lazy {
            MyCompanionObject()
        }
    }
}

// object 反编译后是饿汉式，但是官方说是“懒加载”：是因为只有在调用后
object UserManager {
    // 1. 编译期常量 (const val)
    const val CONST_TAG = "COMPILER_CONSTANT"

    // 2. 普通变量 (val) ，反编译后是static的
    val runtimeTag = "RUNTIME_VARIABLE"

    // object类的init反编译后是 static{}代码块，与普通类不同
    init {
        println("UserManager 被加载并初始化了！ (init 块执行)")
    }

    fun login() {
        println("Login 调用中...")
    }
}

fun main() {
    println("--- 步骤 1: 访问 const val ---")
    println(UserManager.CONST_TAG)

    println("\n--- 步骤 2: 访问普通 val ---")
    println(UserManager.runtimeTag)

    println("\n--- 步骤 3: 调用方法 ---")
    UserManager.login()
}