plugins {
    id("matrix.java.library")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":apt-annotation"))
}
