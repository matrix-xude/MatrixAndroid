package com.xxd.common.util.intent

import android.content.Context
import android.content.Intent

/**
 *    author : xxd
 *    date   : 2020/8/28
 *    desc   : 与Intent相关的工具类
 */
object IntentUtil {

    /**
     * 跳转到一个新的Activity页面
     * 内联+真实泛型
     * @param context 上下文
     */
    inline fun <reified T> startActivity(context: Context) {
        context.startActivity(Intent(context, T::class.java))
    }

    /**
     * 跳转到一个新的Activity页面，同时把intent通过函数传递回去
     * 内联+真实泛型，因为内联函数不支持 nullable属性，所以重构了方法
     * @param context 上下文
     * @param block intent回传函数
     */
    inline fun <reified T> startActivity(context: Context, crossinline block: (Intent) -> Unit) {
        context.startActivity(Intent(context, T::class.java).apply {
            block(this)
        })
    }

}