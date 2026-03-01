package com.xxd.my.gradle.plugin.task

import com.google.gson.Gson
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File
import kotlin.collections.get

abstract class GenerateTask : DefaultTask() {

    // 接收一个外部输入的路径
    @get:Input
    abstract val inputPath: Property<String>

    // 生成一个 .txt 文件
    @TaskAction
    fun generateTxt() {
        val path = inputPath.get()
        println("读取到外部路径: $path")

        // 1. 通过拼接字符串，可以处理不同平台的 / \ 问题
        // "${project.rootDir}".replace("\\","/")
        println("当前绝对路径 + 相对路径: ${project.rootDir} + ${project.path}")

        // 通过次方法，可以系统内部拼接字符串，不会出错
        val projectDir = project.projectDir
        println("project.projectDir: $projectDir")

        // 2. 直接操作文件，不操作字符串
        val file = File(projectDir, path)
        if (file.exists()) {
            println("最终文件路径: ${file.path}")
            val content = file.readText()
            val map = Gson().fromJson(content, HashMap::class.java)
            println("解析到的 JSON 数据: $map")

            val outputStr = "我拿到了需要的数据： ip=${map["ip"]}, port=${map["port"]?.toString()?.toDouble()?.toInt() ?: 0}"

            // 输出到当前项目目录下的 output.txt
            val outputFile = File(project.projectDir, "output.txt")
            outputFile.writeText(outputStr)
            println("结果已输出至: ${outputFile.absolutePath}")
        } else {
            println("文件不存在: ${file.path}")
        }
    }
}