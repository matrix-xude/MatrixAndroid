package com.xxd.kt.basic.pattern

import java.util.regex.Pattern

/**
 *    author : xxd
 *    date   : 2021/9/18
 *    desc   :
 */

fun main() {
    m1()
}

fun m1(){
    val pattern = Pattern.compile("ABC")
    val pattern1 = pattern.pattern()
    println(pattern1)
    val matcher = pattern.matcher("ADFCKABCd")
    val find = matcher.find()
    println("匹配是否成功 $matcher $find")
    val start = matcher.start()
    val end = matcher.end()
    val group = matcher.group()
    println("$start $end $group")

}