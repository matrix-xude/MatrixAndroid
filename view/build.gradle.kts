plugins {
    id("matrix.android.module")
    id("matrix.android.common")
    id("matrix.android.arouter")
    id("com.xxd.gradle.plugin") version "1.0.0"
}

myConfig{
    inputPath.set("config.json")
    // 这样可以返回到上级目录
//    inputPath.set("../config.json")
}

android {
    defaultConfig {
        // AndroidCommonPlugin中自动配置applicationId,这里再赋值即可覆写
//        applicationId = "com.xxd.view3"
    }

}

dependencies {
    implementation(libs.photoview)
}
