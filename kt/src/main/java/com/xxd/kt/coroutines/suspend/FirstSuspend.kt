package com.xxd.kt.coroutines.suspend

import com.xxd.kt.coroutines.basic.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *    author : xxd
 *    date   : 2021/7/16
 *    desc   :
 */
suspend fun main() {
    GlobalScope.launch {
        log(1)
        m1()
        log(2)
        launch {
            log(3)
        }
    }.join()
}

suspend fun m1(){
    Thread.sleep(1000)
}