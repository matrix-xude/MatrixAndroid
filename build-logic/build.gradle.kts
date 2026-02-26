plugins {
    `kotlin-dsl`
}

group = "com.xxd.matrix.buildlogic"

dependencies {
    // 这里的 libs 引用现在可以完美工作了
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}
