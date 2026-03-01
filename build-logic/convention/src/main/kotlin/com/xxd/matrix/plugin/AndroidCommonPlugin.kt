package com.xxd.matrix.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

/**
 * 外部配置扩展
 */
interface AndroidCommonExtension {
    /**
     * 是否加载 :common 模块，默认为 true
     */
    val includeCommon: Property<Boolean>
}

/**
 * 标准化 Android 通用配置插件
 */
class AndroidCommonPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            // 创建扩展配置，默认值为 true
            val extension = extensions.create<AndroidCommonExtension>("androidCommon")
            extension.includeCommon.convention(true)

            pluginManager.apply(libs.findPlugin("kotlin-android").get().get().pluginId)

            // 必须在withPlugin中执行， 它是延迟执行的，但它非常安全，如果外部没有加载该插件，它只是不执行，而不会报错。
            pluginManager.withPlugin(libs.findPlugin("android-application").get().get().pluginId) {
                configureAndroid(libs, extension)
            }
            pluginManager.withPlugin(libs.findPlugin("android-library").get().get().pluginId) {
                configureAndroid(libs, extension)
            }
        }
    }

    private fun Project.configureAndroid(libs: VersionCatalog, extension: AndroidCommonExtension) {
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

            val javaVersionStr = libs.findVersion("javaVersion").get().requiredVersion
            compileOptions {
                sourceCompatibility = JavaVersion.toVersion(javaVersionStr)
                targetCompatibility = JavaVersion.toVersion(javaVersionStr)
            }

            buildFeatures.viewBinding = true
        }

        val jvmTargetStr = libs.findVersion("jvmTarget").get().requiredVersion
        extensions.configure(KotlinAndroidProjectExtension::class.java) {
            /**
             * 这段代码的作用是 全局配置 Kotlin 编译器的 JVM 目标版本。简单来说，它告诉 Kotlin 编译器：“请把我的 Kotlin 代码编译成兼容 Java 17 虚拟机的字节码。”
             * 在 Gradle 的较新版本中（尤其是 Kotlin 1.9.x 及以后），compilerOptions 是配置编译器参数的推荐方式，它取代了旧的 kotlinOptions。
             */
            compilerOptions {
                jvmTarget.set(JvmTarget.fromTarget(jvmTargetStr))
            }
        }

        // 使用 afterEvaluate 确保在配置完成后读取扩展参数，避免因配置顺序导致的读取默认值问题
        afterEvaluate {
            dependencies {
                if (extension.includeCommon.get()) {
                    add("implementation", project(":common"))
                }
                add("implementation", fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

                add("testImplementation", libs.findLibrary("junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-test-ext-junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-test-espresso-core").get())
            }
        }
    }
}
