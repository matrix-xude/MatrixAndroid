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
    }
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}
