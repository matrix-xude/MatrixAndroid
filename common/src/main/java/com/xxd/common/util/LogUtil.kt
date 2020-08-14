package com.xxd.common.util

import com.orhanobut.logger.Logger

/**
 *    author : xxd
 *    date   : 2020/8/14
 *    desc   :
 */
class LogUtil {

    fun test(){
        Logger.t("xyz").d("11111")
        Logger.d("222222")

        val list = listOf("aaa","bbb","ccc","dd","eee","fff")
        val array = arrayOf("aaa","bbb","ccc","dd","eee","fff")
        Logger.d(list)
        Logger.d(array)
        Logger.d(Any())

    }
}