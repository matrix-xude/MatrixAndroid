package com.xxd.matrix.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.getByType

/**
 * 标准化 Android 通用配置插件
 */
class AndroidCommonPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply("org.jetbrains.kotlin.android")

            pluginManager.withPlugin("com.android.application") {
                configureAndroid(libs)
            }
            pluginManager.withPlugin("com.android.library") {
                configureAndroid(libs)
            }
        }
    }

    // 这个不加上， get()报红，而且是一个误报，错误的将这个gradle当成了是在Android 21上跑的代码
    @Suppress("NewApi")
    private fun Project.configureAndroid(libs: VersionCatalog) {
        extensions.configure(BaseExtension::class.java) {
            compileSdkVersion(libs.findVersion("compileSdk").get().requiredVersion.toInt())

            defaultConfig {
                minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
                targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
                versionCode = libs.findVersion("versionCode").get().requiredVersion.toInt()
                versionName = libs.findVersion("versionName").get().requiredVersion

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                resourcePrefix("${project.name}_")

            }

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }

        dependencies {
            add("implementation", project(":common"))
        }
    }
}
