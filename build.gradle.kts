// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
}

tasks.register<Delete>("clean") {
    // 删除根目录的 build
    delete(rootProject.layout.buildDirectory)

    // 遍历所有子项目并删除它们的 build
    subprojects {
        delete(layout.buildDirectory)
    }
}
