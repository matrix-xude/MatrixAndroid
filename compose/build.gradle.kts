plugins {
    id("matrix.android.common")
    id("matrix.android.module")
    id("matrix.android.compose")
    id("matrix.android.arouter")
}

android {

    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }
    }
}

dependencies {
    // 绝大部分依赖已在对应的 convention plugin 中统一引入：
    // 1. matrix.android.common: :common, junit, espresso 等
    // 2. matrix.android.compose: compose-bom, bundles.compose, constraintlayout-compose 等
    // 3. matrix.android.arouter: arouter-api, arouter-compiler
}
