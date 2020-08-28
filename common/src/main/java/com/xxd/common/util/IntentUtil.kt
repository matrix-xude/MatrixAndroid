package com.xxd.common.util

import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 *    author : xxd
 *    date   : 2020/8/28
 *    desc   :
 */
object IntentUtil {

    /**
     * 通用的跳转方法
     * @param listener 给intent赋值参数的回调
     */
    fun startActivity(packageContext: Context, clazz: Class<*>, listener: IntentExtras? = null) {
        val intent = Intent(packageContext, clazz)
        listener?.let {
            it.params(intent)
        }
        packageContext.startActivity(intent)
    }

    /**
     * 给intent赋值的接口
     */
    interface IntentExtras {
        /**
         * 赋值参数
         */
        fun params(intent: Intent)
    }

}