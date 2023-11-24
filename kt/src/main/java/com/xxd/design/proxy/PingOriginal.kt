package com.xxd.design.proxy

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   : 不想暴露给外部使用
 */
class PingOriginal : IPing {
    override fun ping() {
        println("ping 192.168.0.1")
    }
}