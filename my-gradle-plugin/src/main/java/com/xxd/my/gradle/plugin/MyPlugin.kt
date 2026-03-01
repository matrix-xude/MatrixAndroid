package com.xxd.my.gradle.plugin

import com.xxd.my.gradle.plugin.task.HelloTask
import com.xxd.my.gradle.plugin.task.GenerateTask
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

/**
 *    author : xxd
 *    date   : 2026/3/2
 *    desc   : 
 */
class MyPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            println(">>>> 插件加载成功 <<<<")
            // 配置外部传入的参数
            val config = extensions.create<GenerateExtends>("myConfig")

            // 1.注册任务,这里的名字显示在Android Studio 的task上
            tasks.register("hello", HelloTask::class.java)
            tasks.register("generate-txt", GenerateTask::class.java){
                inputPath.set(config.inputPath.convention(""))
            }

            // 2. 统一配置所有继承自 DefaultTask 或特定基类的 Task
            tasks.withType(DefaultTask::class.java).configureEach {
                // 这里的逻辑会应用到插件中所有注册的 DefaultTask 上
                if (name == "hello" || name == "generate-txt") {
                    // 这里的名字显示在Android Studio 的task下的固定包，方便查找
                    group = "my-plugin"
                    description = "演示的task"
                }
            }
        }

    }
}