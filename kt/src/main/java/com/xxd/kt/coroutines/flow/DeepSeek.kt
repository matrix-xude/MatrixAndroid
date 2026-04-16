package com.xxd.kt.coroutines.flow

import com.xxd.kt.coroutines.utils.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

/**
 *    author : xxd
 *    date   : 2026/4/14
 *    desc   : 深入研究Flow流与FlowCollect
 */
class DeepSeek {

    /*
      反编译下面的代码，发现 flow1, flow2 生成的方法完全不同
      核心差异为block代码块是否保存在内存中了！
        1. map 用的是 unsafeFlow方法生成Flow，内部用 crossinline 平铺代码
        2. transform 用的是 flow 方法，生成SafeFlow， SafeFlow 把block保存在自身类中！
     */
    fun m1() {
        val flow = flow {
            for (i in 1..3) {
                emit(i)
            }
        }

        // flow1 不生成工厂模板，直接平铺代码到调用处，节省内存
        val flow1 = flow.map { it * 2 }
        // flow2 生成工厂模板，每次调用产生新的实例。
        val flow2 = flow.transform { emit(it * 2) }
    }

    suspend fun m2() {
        flow {
            for (i in 1..3) {
                emit(i)
            }
        } // 1
            .flowOn(Dispatchers.Default)
            .map { it * 2 } // 2
            .map { it * 2 } // 3
            .flowOn(Dispatchers.IO)
            .map { it * 2 } // 4
            .map { it * 2 } // 5
            .collect { log(it) } // 6
    }

}