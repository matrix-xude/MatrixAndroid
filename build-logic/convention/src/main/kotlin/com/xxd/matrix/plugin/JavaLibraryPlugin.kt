package com.xxd.matrix.plugin

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class JavaLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply(libs.findPlugin("java-library").get().get().pluginId)
            pluginManager.apply(libs.findPlugin("kotlin-jvm").get().get().pluginId)

            val javaVersionStr = libs.findVersion("javaVersion").get().requiredVersion
            extensions.configure(JavaPluginExtension::class.java) {
                sourceCompatibility = JavaVersion.toVersion(javaVersionStr)
                targetCompatibility = JavaVersion.toVersion(javaVersionStr)
            }

            /**
             * 这段代码的作用是 全局配置 Kotlin 编译器的 JVM 目标版本。简单来说，它告诉 Kotlin 编译器：“请把我的 Kotlin 代码编译成兼容 Java 17 虚拟机的字节码。”
             * 在 Gradle 的较新版本中（尤其是 Kotlin 1.9.x 及以后），compilerOptions 是配置编译器参数的推荐方式，它取代了旧的 kotlinOptions。
             */
            val jvmTargetStr = libs.findVersion("jvmTarget").get().requiredVersion
            extensions.configure(KotlinJvmProjectExtension::class.java) {
                compilerOptions {
                    jvmTarget.set(JvmTarget.fromTarget(jvmTargetStr))
                }
            }

            dependencies {
                add("implementation", libs.findLibrary("kotlin-stdlib").get())
            }
        }
    }
}
