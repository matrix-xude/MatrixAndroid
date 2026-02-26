import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.VersionCatalogsExtension

// 1. 所有的 plugins 块必须紧随 import 或在顶部
//plugins {
//    id("org.jetbrains.kotlin.android")
//}

// 2. 获取 Version Catalog
val libs = the<VersionCatalogsExtension>().named("libs")

configure<BaseExtension> {
    val compileSdkVersionStr = libs.findVersion("compileSdk").get().requiredVersion
    compileSdkVersion(compileSdkVersionStr.toInt())

    defaultConfig {
        minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
        targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        // 自动根据模块名设置资源前缀
        resourcePrefix = "${project.name}_"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // 每个模块都默认依赖 :common
    "implementation"(project(":common"))
}
