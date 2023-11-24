package com.xxd.design.proxy

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */
class PingProxy : IPing {

    private val original : IPing = PingOriginal()

    override fun ping() {
        println("雁过拔毛中...")
        original.ping()
    }
}