package com.xxd.matrix.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

/**
 * ARouter 配置插件
 */
class AndroidARouterPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            // 应用 kapt 插件
            pluginManager.apply("kotlin-kapt")

            // 配置 kapt 参数
            extensions.configure(KaptExtension::class.java) {
                arguments {
                    arg("AROUTER_MODULE_NAME", project.name)
                }
            }

            // 必须在 withPlugin 中执行，确保 implementation 配置已存在
            pluginManager.withPlugin("com.android.application") {
                configureDependencies(libs)
            }
            pluginManager.withPlugin("com.android.library") {
                configureDependencies(libs)
            }
        }
    }

    private fun Project.configureDependencies(libs: VersionCatalog) {
        dependencies {
            add("implementation", libs.findLibrary("arouter-api").get())
            add("kapt", libs.findLibrary("arouter-compiler").get())
        }
    }
}
