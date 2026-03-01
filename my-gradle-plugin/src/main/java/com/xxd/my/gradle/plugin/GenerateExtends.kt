package com.xxd.my.gradle.plugin

import org.gradle.api.provider.Property

/**
 *    author : xxd
 *    date   : 2026/3/2
 *    desc   : 
 */
interface GenerateExtends {

    val inputPath : Property<String>
}