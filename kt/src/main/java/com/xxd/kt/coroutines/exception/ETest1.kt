package com.xxd.kt.coroutines.exception

import com.xxd.kt.coroutines.basic.log
import com.xxd.kt.coroutines.context.MyInterceptor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *    author : xxd
 *    date   : 2021/7/9
 *    desc   : coroutine exception 测试1
 */
fun main() {
    m11()
}

fun m11(){
    GlobalScope.launch(MyInterceptor()) {
        log(1)
        try {
            coroutineScope { //①
                log(2)
                launch { // ②
                    log(3)
                    launch { // ③
                        log(4)
                        delay(100)
                        log("--------------")
                        throw ArithmeticException("Hey!!")
                    }
                    log(5)
                }
                log(6)
                val job = launch { // ④
                    log(7)
                    delay(1000)
                    log("------333333------")
                }
                try {
                    log(8)
                    job.join()
                    log("9999")
                } catch (e: Exception) {
                    log("10. $e")
                }
            }
            log("------222222------")
            log(11)
        } catch (e: Exception) {
            log("12. $e")
        }
        log(13)
    }
    Thread.sleep(2000)
}