plugins {
    id("matrix.android.common")
    id("matrix.android.module")
    id("matrix.android.arouter")
    id("matrix.android.compose")
}

android {
    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        // 插件中已包含 viewBinding 和 compose，此处仅保留 aidl
        aidl = true
    }

    sourceSets {
        getByName("main") {
            aidl {
                srcDirs("src/main/aidl", "src/main/xxx/aidl")
            }
            // manifest 的切换逻辑已在 AndroidModulePlugin 中处理
        }
    }
}

dependencies {
    // 绝大部分依赖已在对应的 convention plugin 中统一引入：
    // 1. AndroidCommonPlugin: :common, junit, espresso 等
    // 2. AndroidARouterPlugin: arouter-api, arouter-compiler
    // 3. AndroidComposePlugin: compose-bom, activity-compose, constraintlayout-compose 等
}
