package com.xxd.kt.coroutines.suspend

import com.xxd.kt.coroutines.utils.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
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
            Thread.sleep(100)
            log("m1 resume 1")
            it.resume(1)
        }.start()
    }

    // 自己接手，不手动调用uCont.intercepted()，不会经过拦截器包裹
    suspend fun m2(): Int = suspendCoroutineUninterceptedOrReturn { uCont ->
        Thread {
            Thread.sleep(100)
            log("m2 resume 2")
            uCont.resume(2)
        }.start()
        // 注意! 这里要手动返回 COROUTINE_SUSPENDED ，否则会导致BaseContinuationImpl判断出多次resumeWith
        // 返回值到外部就是调用了一次 resumeWith(COROUTINE_SUSPENDED)
        COROUTINE_SUSPENDED
    }

    // 研究Continuation的包裹链问题
    fun m3() {
        /*步骤1: start后
         a. 创建第一个Continuation : StandaloneCoroutine , 标记为 "C1"
         b. create(Object value, Continuation $completion) : $completion 就是 "C1" , 返回值是 SuspendLambda,标记为"C2"
         c. "C2".intercepted() , 这里的拦截器是 MainCoroutineDispatcher ,创建出一个新的 DispatchedContinuation : "C3"
         d. "C3"创建好后立马resumeWith(Unit)，所以拆箱"C3",根据Dispatcher的逻辑进行调度，所以在block{}代码块中拿到的是"C2"*/
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

    // 研究 job.join 与 deferred.await ; 总结：都用到suspend挂起函数 与 Job生命周期的特性
    fun m4() {
        GlobalScope.launch {
            val job = launch {
                m1()
            }
            val deferred = async {
                m1()
            }
            /*
            suspend方法,延用block代码块内的SuspendLambda逻辑
               1. 内部调用到 joinSuspend() = suspendCancellableCoroutine<Unit> , 挂起函数
               2. invokeOnCompletion(ResumeOnCompletion)监听，回调为 invoke(cause: Throwable?) = continuation.resume(Unit)，用来恢复该 invokeSuspend执行
               3. suspendCancellableCoroutine 函数在内部没有直接resume返回 COROUTINE_SUSPENDED, 所以 join 挂起
            */
            job.join()

            /*
            suspend方法
                1. 内部调用到 awaitSuspend(): Any? = suspendCoroutineUninterceptedOrReturn ， 原生挂起函数
                2. 内部自己用拦截器包裹 AwaitContinuation(uCont.intercepted(), this)
                3. invokeOnCompletion(ResumeAwaitOnCompletion(cont)), 回调为 continuation.resume(state.unboxState() as T)，用来恢复该 invokeSuspend执行
                4. suspendCoroutineUninterceptedOrReturn 函数需要手动返回 COROUTINE_SUSPENDED 挂起，这里是内部做了一个和suspendCancellableCoroutine 一样的 getResult操作，也能挂起
            */
            deferred.await()
        }
    }

    // 对比 launch 和 runBlocking 的区别
    fun m5() {
        // 因为内部的 launch 不是 suspend 方法，不会挂起，所以直接跳出外部 launch 方法
        GlobalScope.launch {
            log("start")
            launch {
                m1()
            }
            launch {
                m2()
            }
            log("end")
        }
    }

    // 对比 launch 和 runBlocking 的区别
    fun m6() {
        // runBlocking 会等待内部所有Job完成后，再退出
        runBlocking {
            log("start")
            launch {
                m1()
            }
            launch {
                m2()
            }
            log("end")
        }
    }

    // 探寻 ScopeCoroutine 拦截Job的双向异常传播问题
    // 阻断的是异常的“非正式通道（Job 树通知）”，而改走“正式通道（函数抛出异常）”
    fun m7() {
        GlobalScope.launch(CoroutineExceptionHandler { _, throwable -> log(throwable) }) {
            launch {
                delay(1000)
                println("C 还活着吗？")
            }

            // B 所在的 coroutineScope
            coroutineScope {
                launch {
                    throw Exception("B 崩了")
                }
            }
        }
    }

    // 只要没有线程切换，没有挂起，Job就是按照层级执行
    fun m8() {
        GlobalScope.launch(NoDefaultInterceptor()) {
            log("start")
            launch {
                repeat(100) { // 让多重复几次
                    log("内部Job 1")
                }
            }
            async {
                log("--内部Job 2")
            }
            log("end")
        }
    }

    //  AbstractCoroutine 的 resumeWith 必须等待自己所以子Job完成，才能继续执行
    fun m9(){
        GlobalScope.launch {
            log("start")
            val job = launch {
                delay(200)
                log("job is completed")
            }
            delay(100)
            log("job is completed : ${job.isCompleted}, is cancelled : ${job.isCancelled}")
            log("end")
        }
    }

}

fun main() {
    val seek = ContinuationSeek()
    seek.m9()

    log("main end")

    Thread.sleep(2000)
}