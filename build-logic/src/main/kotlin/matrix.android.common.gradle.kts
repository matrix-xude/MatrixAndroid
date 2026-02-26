import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.VersionCatalogsExtension

plugins {
    id("org.jetbrains.kotlin.android")
}

// 获取 Version Catalog
val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

// 当 Android Library 插件应用时执行
project.plugins.withId("com.android.library") {
    configureAndroid()
    addCommonDependencies()
}

// 当 Android Application 插件应用时执行
project.plugins.withId("com.android.application") {
    configureAndroid()
    addCommonDependencies()
}

fun configureAndroid() {
    project.extensions.configure<BaseExtension> {
        val compileSdkVersionStr = libs.findVersion("compileSdk").get().requiredVersion
        compileSdkVersion(compileSdkVersionStr.toInt())

        defaultConfig {
            minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
            targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            // 自动配置资源前缀
            resourcePrefix = "${project.name}_"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }
}

/**
 * 添加所有模块共有的依赖
 */
fun addCommonDependencies() {
    project.dependencies.add("implementation", project.project(":common"))
}
