package com.xxd.matrix.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Compose 全家桶 Convention Plugin
 */
class AndroidComposePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

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

            composeOptions {
                kotlinCompilerExtensionVersion = libs.findVersion("kotlinCompilerExtension").get().requiredVersion
            }

            packagingOptions {
                resources {
                    excludes.add("/META-INF/{AL2.0,LGPL2.1}")
                }
            }
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
            add("implementation", libs.findLibrary("androidx-constraintlayout-compose").get())

            add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-manifest").get())
            add("androidTestImplementation", libs.findLibrary("androidx-compose-ui-test-junit4").get())
        }
    }
}
