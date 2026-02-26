plugins {
    alias(libs.plugins.android.application)
    id("matrix.android.common")
    id("matrix.android.arouter")
}

android {
    namespace = "com.xxd.matrixandroid"

    defaultConfig {
        applicationId = libs.versions.applicationId.get()

        // 开启 MultiDex 分包方法
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    // 绝大部分通用依赖（common, junit, espresso, arouter 等）
    // 已由 matrix.android.* 插件统一引入，此处只需声明模块特有依赖

    val isModularize = (findProperty("isModularize") as? String)?.toBoolean() ?: false
    // 根据配置设置依赖
    if (!isModularize) {
        implementation(project(":thread"))
        implementation(project(":view"))
    }
}
