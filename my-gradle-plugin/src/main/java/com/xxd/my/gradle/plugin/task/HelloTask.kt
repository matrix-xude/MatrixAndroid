package com.xxd.my.gradle.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 *    author : xxd
 *    date   : 2026/3/2
 *    desc   : 在 Gradle 中，所有的 Task 类都必须是 可继承的 (Open)。这是因为 Gradle 会在运行时生成你的 Task 的子类，以便注入监控、日志和依赖管理逻辑。
                Java 中：类默认是 open 的，所以没问题。
                Kotlin 中：所有的类默认都是 final 的。如果你不显式加上 open 关键字，Gradle 就会报错。
 */

// 类必须open，
abstract class HelloTask : DefaultTask(){

    @TaskAction
    fun hello(){
        println("你好 , I am Task .")
    }
}