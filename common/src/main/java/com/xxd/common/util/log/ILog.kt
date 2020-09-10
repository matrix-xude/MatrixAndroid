package com.xxd.common.util.log

/**
 *    author : xxd
 *    date   : 2020/9/9
 *    desc   : 打印log应该实现的方法
 */
interface ILog {

    /**
     * Verbose
     */
    fun v(message: String, tag: String? = null)

    /**
     * Debug
     */
    fun d(message: String, tag: String? = null)

    /**
     * Info
     */
    fun i(message: String, tag: String? = null)

    /**
     * Warm
     */
    fun w(message: String, tag: String? = null)

    /**
     * Error
     */
    fun e(message: String, tag: String? = null)

    /**
     * Assert (wtf : What a terrible failure)
     */
    fun wtf(message: String, tag: String? = null)

    /**
     * 打印所有类的方法，可以用来特殊处理某些类
     * ex：array,list,set,map
     */
    fun d(any: Any?, tag: String? = null)

    /**
     * 优雅的打印json
     */
    fun json(json: String?, tag: String? = null)

    /**
     * 优雅的打印xml
     */
    fun xml(xml: String?, tag: String? = null)


}