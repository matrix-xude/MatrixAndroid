package com.xxd.matrix.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * 模块化/组件化 切换插件
 */
class AndroidModulePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val isModularize = findProperty("isModularize")?.toString()?.toBoolean() ?: false

            if (isModularize) {
                pluginManager.apply(libs.findPlugin("android-application").get().get().pluginId)
            } else {
                pluginManager.apply(libs.findPlugin("android-library").get().get().pluginId)
            }

            extensions.configure(BaseExtension::class.java) {
                if (isModularize) {
                        defaultConfig {
                            // 当作为 application 运行时需要 application id
                            applicationId = "com.xxd.${project.name}"
                    }
                }

                sourceSets.getByName("main") {
                    if (isModularize) {
                        manifest.srcFile("src/main/module/AndroidManifest.xml")
                    } else {
                        manifest.srcFile("src/main/AndroidManifest.xml")
                    }
                }
            }
        }
    }
}
