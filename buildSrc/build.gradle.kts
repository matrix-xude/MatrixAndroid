plugins {
    `kotlin-dsl`
}

dependencies {
    // 使用 compileOnly 避免与根目录的插件版本冲突
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}
