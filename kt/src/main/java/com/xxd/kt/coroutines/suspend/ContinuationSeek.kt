package com.xxd.kt.coroutines.suspend

import com.xxd.kt.coroutines.basic.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume

/**
 *    author : xxd
 *    date   : 2026/3/24
 *    desc   : Continuation 是一个接口，表示挂起点后将要做的事情
 *
 */
class ContinuationSeek {

    // 调用系统方法，内部代码调用了uCont.intercepted()，会经过拦截器包裹
    suspend fun m1() = suspendCancellableCoroutine {
        Thread {
            Thread.sleep(10)
            it.resume(1)
        }.start()
    }

    // 自己接手，不手动调用uCont.intercepted()，不会经过拦截器包裹
    suspend fun m2(): Int = suspendCoroutineUninterceptedOrReturn { uCont ->
        Thread {
            Thread.sleep(10)
            uCont.resume(1)
        }.start()
        // 注意! 这里要手动返回 COROUTINE_SUSPENDED ，否则会导致BaseContinuationImpl判断出多次resumeWith
        // 返回值到外部就是调用了一次 resumeWith(COROUTINE_SUSPENDED)
        COROUTINE_SUSPENDED
    }

    // 研究Continuation的包裹链问题
    fun m3() {
        // 步骤1: start后
        //  a. 创建第一个Continuation : StandaloneCoroutine , 标记为 "C1"
        //  b. create(Object value, Continuation $completion) : $completion 就是 "C1" , 返回值是 SuspendLambda,标记为"C2"
        //  c. "C2".intercepted() , 这里的拦截器是 MainCoroutineDispatcher ,创建出一个新的DispatchedContinuation : "C3"
        //  d. "C3"创建好后立马resumeWith(Unit)，所以拆箱"C3",根据Dispatcher的逻辑进行调度，所以在block{}代码块中拿到的是"C2"
        GlobalScope.launch(Dispatchers.Default) {
            log(": start")
            // 步骤2: block 代码块内的 Continuation 就是"C2"
            // m2方法拿到的就是"C2"，SuspendLambda
            val m1 = m1()
            log(m1) // 经过了拦截器，打印还是Default
            val m2 = m2()
            log(m2) // 不经过拦截器，打印了手动Thread的线程
        }
    }

    fun m4() {
        val mutex = Mutex()
        val job1 = GlobalScope.launch(Dispatchers.Default) {
            mutex.withLock {
                m1()
            }
        }

        val job2 = GlobalScope.async(Dispatchers.Default) {
            m1()
        }

    }


}

fun main() {
    val seek = ContinuationSeek()
    seek.m3()
//
//    GlobalScope.launch(MyInterceptor()) {
////        val m1 = seek.m1()
////        log(m1)
//        val m2 = seek.m2()
//        log(m2)
//    }
//
    Thread.sleep(100)
}