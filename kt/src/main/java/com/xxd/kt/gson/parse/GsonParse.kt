package com.xxd.kt.gson.parse

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.xxd.kt.gson.bean.Partner
import com.xxd.kt.gson.bean.Partner2

/**
 *    author : xxd
 *    date   : 2021/7/7
 *    desc   : Gson 解析分析
 */

val gson = Gson()
val gson2: Gson = GsonBuilder().registerTypeAdapter(String::class.java, StringAdapter()).create()

const val json1 = """
    {"name":"Amber","attack":206,"defence":189,"criticalRate":0.32,"criticalDMG":0.88,"comeOnTheStage":true,"prologue":"侦查骑士，登场！"}
"""
const val json2 = """
    {"attack":206,"criticalDMG":0.88,"comeOnTheStage":true,"prologue":"侦查骑士，登场！"}
"""
const val json3 = """
    {"name":null,"attack":206,"criticalDMG":0.88,"prologue":"侦查骑士，登场！"}
"""
const val json4 = """
    {"attack":206,"criticalDMG":0.88,"prologue":"侦查骑士，登场！"}
"""

fun main() {
    m2()
}

fun m1() {
    val amber = Partner2(null, 206, 189, 0.32f, 1.88f, true, "侦查骑士，登场！")
    val amberJson = gson.toJson(amber)
    val amberJson2 = gson2.toJson(amber)
    println(amberJson)
    println(amberJson2)
}

fun m2() {
    val bean1 = gson.fromJson(json4, Partner::class.java)
    println(bean1)
    val bean2 = gson2.fromJson(json4, Partner::class.java)
    println(bean2)
}