package com.xxd.apt_annotation

/**
 *    author : xxd
 *    date   : 2020/8/10
 *    desc   : 测试的注解类
 */
@Target(AnnotationTarget.FIELD,AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE) // apt处理只需要源码级别保留即可
annotation class AnnotationTest(val value: String, val code: Int)