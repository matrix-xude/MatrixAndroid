package com.xxd.common.util.log

/**
 *    author : xxd
 *    date   : 2020/9/10
 *    desc   : 打印工具类，此处只是一个代理
 */
object LogUtil : ILog {

    /**
     * 代理类使用的真实打印对象，可以随便替换为自己需要的打印对象
     */
    private val logClient: ILog = LogImpl()

    override fun v(message: String, tag: String?) {
        logClient.v(message, tag)
    }

    override fun d(message: String, tag: String?) {
        logClient.d(message, tag)
    }

    override fun d(any: Any?, tag: String?) {
        logClient.d(any, tag)
    }

    override fun i(message: String, tag: String?) {
        logClient.i(message, tag)
    }

    override fun w(message: String, tag: String?) {
        logClient.w(message, tag)
    }

    override fun e(message: String, tag: String?) {
        logClient.e(message, tag)
    }

    override fun wtf(message: String, tag: String?) {
        logClient.wtf(message, tag)
    }

    override fun json(json: String?, tag: String?) {
        logClient.json(json, tag)
    }

    override fun xml(xml: String?, tag: String?) {
        logClient.xml(xml, tag)
    }
}