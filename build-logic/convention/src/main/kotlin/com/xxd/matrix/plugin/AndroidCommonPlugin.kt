package com.xxd.matrix.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


/**
 * 标准化 Android 通用配置插件
 */
class AndroidCommonPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply(libs.findPlugin("kotlin-android").get().get().pluginId)

            // 必须在withPlugin中执行， 它是延迟执行的，但它非常安全，如果外部没有加载该插件，它只是不执行，而不会报错。
            pluginManager.withPlugin(libs.findPlugin("android-application").get().get().pluginId) {
                configureAndroid(libs)
            }
            pluginManager.withPlugin(libs.findPlugin("android-library").get().get().pluginId) {
                configureAndroid(libs)
            }
        }
    }

    private fun Project.configureAndroid(libs: VersionCatalog) {
        extensions.configure(BaseExtension::class.java) {
            compileSdkVersion(libs.findVersion("compileSdk").get().requiredVersion.toInt())

            defaultConfig {
                // 【自动化 Namespace】基于目录结构生成唯一包名，如 :feature:login -> com.xxd.login
                val modulePath = project.path.replace(":", ".").removePrefix(".")
                val basePackage = "com.xxd"
                val generatedNamespace =
                    if (modulePath.isNotEmpty()) "$basePackage.$modulePath" else basePackage

                namespace = generatedNamespace

                minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
                targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
                versionCode = libs.findVersion("versionCode").get().requiredVersion.toInt()
                versionName = libs.findVersion("versionName").get().requiredVersion

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                // 约束资源名字前缀
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

            buildFeatures.viewBinding = true
        }

        tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                // 解决ktx的编译问题，默认使用的是1.6
                jvmTarget = "1.8"
            }
        }

        dependencies {
            add("implementation", project(":common"))
            add("implementation", fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

            add("testImplementation", libs.findLibrary("junit").get())
            add("androidTestImplementation", libs.findLibrary("androidx-test-ext-junit").get())
            add("androidTestImplementation", libs.findLibrary("androidx-test-espresso-core").get())
        }
    }
}
