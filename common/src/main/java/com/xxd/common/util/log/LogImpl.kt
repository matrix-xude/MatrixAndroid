package com.xxd.common.util.log

import com.orhanobut.logger.Logger

/**
 *    author : xxd
 *    date   : 2020/9/10
 *    desc   : 真实打印的log的类，
 *    此处使用 com.orhanobut.logger.Logger 打印，Logger 需要初始化,放在BaseApplication中
 */
class LogImpl : ILog {

    override fun v(message: String, tag: String?) {
        Logger.t(tag).v(message)
    }

    override fun d(message: String, tag: String?) {
        Logger.t(tag).d(message)
    }

    override fun d(any: Any?, tag: String?) {
        Logger.t(tag).d(any)
    }

    override fun i(message: String, tag: String?) {
        Logger.t(tag).i(message)
    }

    override fun w(message: String, tag: String?) {
        Logger.t(tag).w(message)
    }

    override fun e(message: String, tag: String?) {
        Logger.t(tag).e(message)
    }

    override fun wtf(message: String, tag: String?) {
        Logger.t(tag).wtf(message)
    }

    override fun json(json: String?, tag: String?) {
        Logger.t(tag).json(json)
    }

    override fun xml(xml: String?, tag: String?) {
        Logger.t(tag).xml(xml)
    }
}