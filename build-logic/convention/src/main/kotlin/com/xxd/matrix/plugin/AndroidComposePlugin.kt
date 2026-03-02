package com.xxd.matrix.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

/**
 * Compose 全家桶 Convention Plugin
 */
class AndroidComposePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            // kotlin 2.0后，compose由kotlin自带
            pluginManager.apply(libs.findPlugin("kotlin-compose").get().get().pluginId)

            // 必须在withPlugin中执行， 它是延迟执行的，但它非常安全，如果外部没有加载该插件，它只是不执行，而不会报错。
            pluginManager.withPlugin(libs.findPlugin("android-application").get().get().pluginId) {
                configureCompose(libs)
            }
            pluginManager.withPlugin(libs.findPlugin("android-library").get().get().pluginId) {
                configureCompose(libs)
            }
        }
    }

    private fun Project.configureCompose(libs: VersionCatalog) {
        extensions.configure(BaseExtension::class.java) {
            buildFeatures.compose = true

            // kotlin 2.0后，这里必须丢弃
            /*composeOptions {
                kotlinCompilerExtensionVersion = libs.findVersion("kotlinCompilerExtension").get().requiredVersion
            }*/

            packagingOptions {
                resources {
                    excludes.add("/META-INF/{AL2.0,LGPL2.1}")
                }
            }
        }

        //  kotlin 2.2可以跳过“重组”;在 Compose 中，当状态发生变化时，系统会尝试重新运行函数。如果函数检测到输入的参数没有变化，它就可以直接“跳过”执行，保留上一次的结果。
        extensions.configure(ComposeCompilerGradlePluginExtension::class.java) {
            // 开启强跳过模式
            featureFlags.add(ComposeFeatureFlag.StrongSkipping)

            // 配置报告输出路径
            // 使用 project.layout 获取构建目录
            val metricsDir = layout.buildDirectory.dir("compose_metrics")
            val reportsDir = layout.buildDirectory.dir("compose_reports")

            metricsDestination.set(metricsDir)
            reportsDestination.set(reportsDir)
        }

        dependencies {
            val composeBom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(composeBom))
            add("androidTestImplementation", platform(composeBom))

            add("implementation", libs.findLibrary("androidx-activity-compose").get())
            add("implementation", libs.findLibrary("androidx-compose-ui").get())
            add("implementation", libs.findLibrary("androidx-compose-ui-graphics").get())
            add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
            add("implementation", libs.findLibrary("androidx-compose-material3").get())
            add("implementation", libs.findLibrary("androidx-compose-material-icons-extended").get())
            add("implementation", libs.findLibrary("androidx-constraintlayout-compose").get())

            add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-manifest").get())
            add("androidTestImplementation", libs.findLibrary("androidx-compose-ui-test-junit4").get())
        }
    }
}
