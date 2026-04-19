package com.xxd.kt.coroutines.flow

import com.xxd.kt.coroutines.utils.log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 *    author : xxd
 *    date   : 2026/4/19
 *    desc   : Flow的异常的核心： 必须透明，不能在异常传播链中隐藏异常。必须显式的捕获 or 向上抛出
 */
class FlowException {

    // Flow 可以在collect中通过try catch捕获所有异常
    fun m1() {
        val flow = flow {
            emit(1)
            // 在flow链路中，异常1触发时机，晚于异常2，所以不会捕获这里的异常
            throw IllegalStateException("emit 异常") // 异常部位 1
            emit(2)
            emit(3)
        }

        runBlocking {
            try {
                flow.collect {
                    log(it)
                    throw RuntimeException("collect 异常") // 异常部位 2
                }
            } catch (e: Exception) {
                log(e)
            }
        }
    }

    // catch 操作符可以抓到上游的异常，不能抓下游异常
    fun m2() {
        val flow = flow {
            emit(1)
            throw CancellationException("emit 异常") // 异常部位 1
            emit(2)
            emit(3)
        }.catch { e ->  // 会终断flow链路，因为是在回调到collect中处理的
            log("catch: $e")
            emit(-1)
        }

        runBlocking {
            try {
                flow.collect {
                    log(it)
                }
            } catch (e: Exception) { // 因为flow中有catch，所以这里不会捕获异常
                log("collect : $e")
            }
        }
    }

    // flow 错误演示，上游捕获了下游异常
    fun m3() {
        val flow = flow {
            try {
                emit(1)
                emit(2)
                emit(3)
            } catch (e: Exception) {
                log(e)
                // 这里会捕获到下游异常，再次emit,在safeCollector中会检测这种操作。抛出 violated 异常
                emit(-1)
            }
        }

        runBlocking {
            flow.collect {
                log(it)
                throw RuntimeException("collect 异常")
            }
        }
    }

    // 监听flow completion , 可以使用传统 finally
    fun m4() {
        val flow = (1..3).asFlow()

        runBlocking {
            try {
                flow.collect {
                    log(it)
                }
            } finally {
                log("Done")
            }
        }
    }

    // 监听flow completion , 也可以使用 onComplete
    fun m5() {
        val flow = (1..3).asFlow()
            .onCompletion { log("$it Done") } // it 表示异常，正常结束为null

        runBlocking {
            flow.collect {
                log(it)
            }
        }
    }

}

fun main() {
    val flowException = FlowException()
    flowException.m5()
}