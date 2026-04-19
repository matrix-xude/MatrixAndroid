package com.xxd.kt.coroutines.flow

import com.xxd.kt.coroutines.utils.log
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking

/**
 *    author : xxd
 *    date   : 2026/4/19
 *    desc   : Flow的一些比较少用的方法总结
 */
class FlowOther {

    // 类似addEventListener 的事件监听
    fun events(): Flow<Int> = (1..3).asFlow().onEach { delay(100) }

    // onEach 类似addEventListener，但是没有collect 不会执行
    fun m1() {
        runBlocking {
            events()
                .onEach { log("Event: $it") }
                .collect {}
            log("Done")
        }
    }

    // launchIn 会把执行条件发生在指定的CoroutineScope中
    fun m2() {
        runBlocking {
            events()
                .onEach { log("Event: $it") }
                // launchIn 到runBlocking的Scope中运行，所以会阻止主线程结束
                .launchIn(this)
            log("Done")
        }
    }

    // launchIn 会把执行条件发生在指定的CoroutineScope中，指定一个特殊的CoroutineScope，就与当前运行的Scope无关了
    fun m3() {
        runBlocking {
            events()
                .onEach { log("Event: $it") }
                // launchIn到另一个CoroutineScope中，因为不在 runBlocking的Scope中运行，生命周期与runBlocking无关
                .launchIn(CoroutineScope(Dispatchers.IO + CoroutineName("MyCoroutine")))
            log("Done")

            // 因为launchIn到另一个CoroutineScope中，所以需要延时，防止主线程退出
            delay(1000)
        }
    }

    // 因为可以调用job.cancel函数,所以不需要类似 removeEventListener 函数
    fun m4() {
        val flow = flow {
            for (i in 1..10) {
                log("Emitting $i")
                emit(i)
            }
        }

        // runBlocking 不会内部处理 JobCancellationException，该方法会抛出异常，放到launch中就不会抛出该异常
        runBlocking {
            flow.collect {
                log(it)
                if (it == 3) {
                    coroutineContext.job.cancel()
                }
            }
        }
    }

    // asFlow() 使用的是 unsafeFlow 方法创建，没有 safeCollector，不会自动检测Job cancel状态，所以不会中断执行逻辑
    fun m5() {
        val flow = (1..10).asFlow()

        // runBlocking 不会内部处理 JobCancellationException，该方法会抛出异常，放到launch中就不会抛出该异常
        runBlocking {
            flow.collect {
                log(it)
                if (it == 3) {
                    coroutineContext.job.cancel()
                }
            }
        }
    }

    // 与 m5() 区别就是加上了cancel 检测
    fun m6() {
        val flow = (1..10).asFlow().cancellable() // 加上中断检测

        // runBlocking 不会内部处理 JobCancellationException，该方法会抛出异常，放到launch中就不会抛出该异常
        runBlocking {
            flow.collect {
                log(it)
                if (it == 3) {
                    coroutineContext.job.cancel()
                }
            }
        }
    }
}

fun main() {
    val flowOther = FlowOther()
    flowOther.m6()
}