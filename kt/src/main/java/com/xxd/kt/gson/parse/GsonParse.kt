package com.xxd.kt.gson.parse

import com.google.gson.Gson
import com.xxd.kt.gson.bean.Partner

/**
 *    author : xxd
 *    date   : 2021/7/7
 *    desc   : Gson 解析分析
 */

val gson = Gson()

val amberJsonStr = "{\"name\":\"Amber\",\"attack\":206,\"defence\":189,\"criticalRate\":0.32,\"criticalDMG\":0.88,\"comeOnTheStage\":true,\"prologue\":\"侦查骑士，登场！\"}"

fun main() {
    val amber = Partner("Amber", 206, 189, 0.32f, 0.88f, true, "侦查骑士，登场！")
    val amberJson = gson.toJson(amber)
    println(amberJson)
}

fun m1(){
    val amber = Partner("Amber", 206, 189, 0.32f, 0.88f, true, "侦查骑士，登场！")
    val amberJson = gson.toJson(amber)
    println(amberJson)
}

fun m2(){

}