// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

tasks.register<Delete>("clean") {
    group = "build" // 显式指定分组，方便在面板中查找
    delete(rootProject.layout.buildDirectory)
    // 使用 map 转换确保在配置阶段正确获取路径
    delete(subprojects.map { it.layout.buildDirectory })
}

// 1. 注册一个名为 "hello" 的基础任务
tasks.register("hello") {
    // 2. 分组（决定它出现在 Gradle 面板的哪个文件夹下）
    group = "custom"
    // 3. 描述（鼠标悬停时显示的说明）
    description = "这是一个演示任务"

    // 4. 执行逻辑（必须写在 doLast 闭包中，否则同步时就会运行）
    doLast {
        println("${description} Hello from Kotlin DSL!")
    }
}
