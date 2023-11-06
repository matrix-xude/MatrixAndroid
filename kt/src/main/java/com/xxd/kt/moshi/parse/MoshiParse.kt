package com.xxd.kt.moshi.parse

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.xxd.kt.moshi.bean.Partner

/**
 *    author : xxd
 *    date   : 2023/11/6
 *    desc   : moshi用例
 */

val moshi = Moshi.Builder().build()

const val json1 = """
    {"name":"Amber","attack":"20.01","defence":189,"criticalRate":0.32,"criticalDMG":0.88,"comeOnTheStage":true,"prologue":"侦查骑士，登场！"}
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

fun main(){
    m1()
}

fun m1(){
    val adapter : JsonAdapter<Partner> = moshi.adapter(Partner::class.java)
    val fromJson = adapter.fromJson(json1)
    println(fromJson)
}