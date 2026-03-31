package com.xxd.kt.coroutines.job

import com.xxd.kt.coroutines.utils.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.onTimeout
import kotlinx.coroutines.selects.select

/**
 *    author : xxd
 *    date   : 2026/3/18
 *    desc   : Select 可以阻塞式等待的局限性
 */
class SelectSearch {

    fun m1() {
        GlobalScope.launch {

            val deferred1 = async {
                delay(100)
                log("deferred1 finished")
                1
            }
            val deferred2 = async {
                delay(199)
                log("deferred2 finished")
                2
            }


            // Select使用
            val select = select<Int> {
                deferred1.onAwait { it }
                deferred2.onAwait { it }
            }

            log(select)
        }
    }

    fun m2() {
        GlobalScope.launch {
            val job1 = launch {
                delay(100)
                log(1)
            }
            val job2 = launch {
                delay(200)
                log(2)
            }

            select {
                job1.onJoin { log("1竞速赢了") }
                job2.onJoin { log("2竞速赢了") }
                onTimeout(1000){

                }
            }

            log("高端大气")
        }
    }

    fun m3() {

    }
}

fun main() {
    val search = SelectSearch()

    search.m2()

    Thread.sleep(1000)
}