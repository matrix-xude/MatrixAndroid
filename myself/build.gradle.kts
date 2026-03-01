plugins {
    id("matrix.android.common")
    id("matrix.android.module")
    id("matrix.android.arouter")
}

android {

    buildFeatures {
        // 开启模块特有的 aidl
        aidl = true
    }
}

dependencies {
    // 模块特有依赖
    kapt(libs.room.compiler)
    implementation(libs.easypopup)

    androidTestImplementation(libs.androidx.navigation.testing)
}
