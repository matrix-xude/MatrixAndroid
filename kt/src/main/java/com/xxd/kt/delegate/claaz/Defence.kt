package com.xxd.kt.delegate.claaz

/**
 *    author : xxd
 *    date   : 2021/7/1
 *    desc   : 防御接口
 */
interface Defence {

    /**
     * 防御攻击
     * @param attackAmount 攻击次数
     */
    fun defenceAttack(attackAmount: Int)

    /**
     * 展示宣言
     */
    fun showDeclaration()
}