plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

group = "com.xxd.matrix.buildlogic"

gradlePlugin {
    plugins {
        create("androidCommon") {
            id = "matrix.android.common"
            implementationClass = "com.xxd.matrix.plugin.AndroidCommonPlugin"
        }
        create("androidCompose") {
            id = "matrix.android.compose"
            implementationClass = "com.xxd.matrix.plugin.AndroidComposePlugin"
        }
        create("androidModule") {
            id = "matrix.android.module"
            implementationClass = "com.xxd.matrix.plugin.AndroidModulePlugin"
        }
        create("androidARouter") {
            id = "matrix.android.arouter"
            implementationClass = "com.xxd.matrix.plugin.AndroidARouterPlugin"
        }
        create("javaLibrary") {
            id = "matrix.java.library"
            implementationClass = "com.xxd.matrix.plugin.JavaLibraryPlugin"
        }
    }
}

// 关键：将 toml 中定义的插件作为 build-logic 的依赖引入
// 这样在你的 Kotlin 插件代码里 apply 时，classpath 才有对应的版本
dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}
