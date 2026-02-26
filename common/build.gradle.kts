plugins {
    alias(libs.plugins.android.library)
    id("matrix.android.common")
    id("matrix.android.arouter")
    id("kotlin-parcelize")
}

// 由于这是 common 模块自身，不需要再依赖 :common
androidCommon {
    includeCommon.set(false)
}

android {
    // namespace 已在 AndroidCommonPlugin 中根据目录结构自动生成为 com.xxd.common
    // compileSdk, minSdk, targetSdk, testInstrumentationRunner 等均已在插件中统一配置

    defaultConfig {
        // 开启 MultiDex 分包
        multiDexEnabled = true
    }

    buildFeatures {
        aidl = true
        // viewBinding = true // 插件中已默认开启
    }

    // AIDL 源码目录配置
    sourceSets {
        getByName("main") {
            aidl {
                srcDirs("src/main/aidl")
            }
        }
    }
}

dependencies {
    // Android 基础库
    api(libs.androidx.appcompat)

    // Kotlin 相关
    api(libs.kotlin.stdlib)
    api(libs.kotlin.reflect)
    api(libs.kotlin.coroutines.core)
    api(libs.kotlin.coroutines.android)

    // KTX & Lifecycle
    api(libs.androidx.core.ktx)
    api(libs.androidx.fragment.ktx)
    api(libs.bundles.lifecycle)
    api(libs.androidx.lifecycle.runtime.compose)

    // 常用工具
    api(libs.androidx.multidex)
    api(libs.logger)
    api(libs.rxpermissions)
    // api(libs.arouter.api) // matrix.android.arouter 插件已引入 api 和 kapt 编译器
    api(libs.immersionbar)
    api(libs.immersionbar.components)
    api(libs.immersionbar.ktx)
    api(libs.rxTool.kit)
    api(libs.jackson.core)
    api(libs.jackson.databind)
    api(libs.gson)
    api(libs.moshi)
    api(libs.rxjava)
    api(libs.rxandroid)
    api(libs.okio)
    api(libs.okhttp)
    api(libs.retrofit)
    api(libs.retrofit.converter.gson)
    api(libs.glide)
    api(libs.room.runtime)

    // Navigation
    api(libs.androidx.navigation.fragment)
    api(libs.androidx.navigation.ui)
    api(libs.androidx.navigation.compose)
    api(libs.androidx.navigation.fragment.ktx)
    api(libs.androidx.navigation.ui.ktx)
    api(libs.androidx.navigation.dynamic.features.fragment)

    // 系统控件
    api(libs.androidx.constraintlayout)
    api(libs.androidx.recyclerview)
    api(libs.androidx.cardview)
    api(libs.androidx.viewpager2)
    api(libs.flexbox)
    api(libs.material)

    // 第三方控件
    api(libs.brvah)
    api(libs.banner)
    api(libs.magicIndicator)
    api(libs.refresh.layout.kernel)
    api(libs.refresh.header.classics)
}
