package com.xxd.kt.gson.parse

import com.google.gson.Gson
import com.xxd.kt.gson.bean.Partner
import com.xxd.kt.gson.bean.Partner2
import com.xxd.kt.gson.bean.Partner3
import com.xxd.kt.gson.bean.Partner4

/**
 *    author : xxd
 *    date   : 2021/7/7
 *    desc   : Gson 解析分析
 */

val gson = Gson()

const val json1 = """
    {"name":"Amber","attack":206,"defence":189,"criticalRate":0.32,"criticalDMG":0.88,"comeOnTheStage":true,"prologue":"侦查骑士，登场！"}
"""
const val json2 = """
    {"attack":206,"criticalDMG":0.88,"comeOnTheStage":true,"prologue":"侦查骑士，登场！"}
"""
const val json3 = """
    {"attack":206,"criticalDMG":0.88,"prologue":"侦查骑士，登场！"}
"""

fun main() {
    m2()
}

fun m1(){
    val amber = Partner("Amber", 206, 189, 0.32f, 1.88f, true, "侦查骑士，登场！")
    val amberJson = gson.toJson(amber)
    println(amberJson)
}

fun m2(){
    val bean1 = gson.fromJson(json3, Partner4::class.java)
    println(bean1)
}