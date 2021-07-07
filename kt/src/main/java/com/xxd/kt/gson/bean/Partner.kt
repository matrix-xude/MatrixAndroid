package com.xxd.kt.gson.bean

/**
 *    author : xxd
 *    date   : 2021/7/7
 *    desc   : 小伙伴，有各种属性
 */
data class Partner(
    val name: String,
    val attack: Int, // 攻击力
    val defence: Int, // 防御力
    val criticalRate: Float = 0.05f, // 暴击率（0-100%),默认5%
    val criticalDMG: Float = 0.50f, // 暴击伤害 （0-∞），默认50%
    var comeOnTheStage: Boolean, // 当前是否出场
    var prologue: String? // 开场白
)
