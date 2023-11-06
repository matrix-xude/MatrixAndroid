package com.xxd.myself.kt.moshi

import com.squareup.moshi.JsonClass

/**
 *    author : xxd
 *    date   : 2023/11/6
 *    desc   : 小伙伴，有各种属性（完全copy gson的bean）
 */
@JsonClass(generateAdapter = true)
data class Partner(
    var name: String,
    val attack: Int, // 攻击力
    val defence: Int, // 防御力
    val criticalRate: Float = 0.05f, // 暴击率（0-100%),默认5%
    val criticalDMG: Float = 0.5f, // 暴击伤害 （0-∞），默认50%
    var comeOnTheStage: Boolean, // 当前是否出场
    var prologue: String? // 开场白
)

data class Partner2(
    var name: String?,
    val attack: Int, // 攻击力
    val defence: Int, // 防御力
    val criticalRate: Float = 0.05f, // 暴击率（0-100%),默认5%
    val criticalDMG: Float = 0.5f, // 暴击伤害 （0-∞），默认50%
    var comeOnTheStage: Boolean, // 当前是否出场
    var prologue: String? // 开场白
)

data class Partner3(
    var name: String?,
    val attack: Int, // 攻击力
    val defence: Int, // 防御力
    val criticalRate: Float = 0.05f, // 暴击率（0-100%),默认5%
    val criticalDMG: Float = 0.5f, // 暴击伤害 （0-∞），默认50%
    var comeOnTheStage: Boolean, // 当前是否出场
    var prologue: String? // 开场白
)

data class Partner4(
    var name: String,
    val attack: Int, // 攻击力
    val defence: Int, // 防御力
    val criticalRate: Float = DEFAULT_CORTICAL_RATE, // 暴击率（0-100%),默认5%
    val criticalDMG: Float = DEFAULT_CORTICAL_DMG, // 暴击伤害 （0-∞），默认50%
    var comeOnTheStage: Boolean = false, // 当前是否出场
    var prologue: String? // 开场白
) {
    companion object {
        const val DEFAULT_CORTICAL_RATE = 0.05f
        const val DEFAULT_CORTICAL_DMG = 0.5f
    }
    constructor() : this("", 0, 0, DEFAULT_CORTICAL_RATE, DEFAULT_CORTICAL_DMG, false, null)
}
