group = "com.xxd.gradle.plugin" // 这就是你的 GroupId,保证全球唯一
version = "1.0.0" // 需要发布的版本

plugins {
    `kotlin-dsl`  // 用 Kotlin 写 Task
    `java-gradle-plugin` // 必须引入这个核心插件，写当前脚本
    `maven-publish` // 发布maven必须
    id("matrix.java.library")
}

// 引入 `java-gradle-plugin` ，才有此配置
gradlePlugin {
    plugins{
        // 这里的 create 名称可以随便起，它决定了发布到 Maven 时的 Artifact ID（重要）
        // maven库是通过 group:artifactId:version来命名的: com.xxd.gradle.plugin:my-hello:1.0.0
        create("my-plugin"){
            id = "com.xxd.gradle.plugin"               // 别人引用时的 ID
            implementationClass = "com.xxd.my.gradle.plugin.MyPlugin" // 你的 Plugin 类路径
        }
    }
}

// 引入 `maven-publish` ，才有此配置
publishing {
    repositories{
        maven {
            // 这里替换为你的私服地址 (Nexus/Artifactory)
            url = uri("https://repo.yourcompany.com/repository/maven-releases/")
            credentials {
                username = "xxd"
                password = "123"
            }
        }
    }
}

dependencies {
    implementation(libs.gson)
}
