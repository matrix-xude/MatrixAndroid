package com.xxd.kt.coroutines.job

import com.xxd.kt.coroutines.utils.log
import com.xxd.kt.coroutines.utils.printContextElements
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.coroutines.cancellation.CancellationException

/**
 *    author : xxd
 *    date   : 2026/3/17
 *    desc   : 探寻Job在协程中的作用
 */
fun main() {
    m7()
}

// 展示Job如何生成，状态怎么流转
fun m1() {
    // launch 启动，返回Job
    val job = GlobalScope.launch(Dispatchers.Default + CoroutineName("Job探寻")) {
        log(1)
        // 打印当前scope的所有Element,主要是查看Job
        printContextElements(this.coroutineContext)
        launch {
            log(2)
            // 打印当前子scope的所有Element,主要是查看Job
            printContextElements(this.coroutineContext)
        }
    }
    Thread.sleep(100)
    log(job)
}

// 展示Job如何生成，状态怎么流转
fun m2() {
    // runBlocking 会阻塞当前线程，等待runBlocking 中的{}执行完毕。且不会返回 Job
    runBlocking(Dispatchers.Default + CoroutineName("Job探寻")) {
        log(1)
        printContextElements(this.coroutineContext)
        launch {
            log(2)
            printContextElements(this.coroutineContext)
        }
    }
}

// Job怎么控制异常传播
fun m3() {
    // 这里使用的是普通Job
    val coroutineScope =
        CoroutineScope(Dispatchers.Default + Job() + CoroutineExceptionHandler { _, throwable ->
            log(throwable)
        })

    // 子任务1
    val job1 = coroutineScope.launch() {
        delay(100)
        log(1)
    }

    // 子任务2
    val job2 = coroutineScope.launch {
        1 / 0
        log(2)
    }

    Thread.sleep(500)
    log(job1)
    log(job2)
}

// Job怎么控制异常传播
fun m4() {
    // 这里使用的是SupervisorJob
    val coroutineScope =
        CoroutineScope(Dispatchers.Default + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
            log(throwable)
        })

    // 子任务1
    val job1 = coroutineScope.launch() {
        delay(100)
        log(1)
    }

    // 子任务2
    val job2 = coroutineScope.launch {
        1 / 0
        log(2)
    }

    Thread.sleep(500)
    log(job1)
    log(job2)
}

// Job 的cancel机制
fun m5() {
    val job = GlobalScope.launch {
        // 这样会一直打印，因为类似于Thread.interrupt,是一种“协作”协议，必须有挂起点才能取消
        /*while (true){
            log("运行中……")
            yield()
        }*/

        // 每次判断是否存活，可以检测到是否取消
        /*while (isActive){
            log("运行中……")
        }*/


        // yield() 会主动让出 CPU 执行权，并立即检查当前协程是否已被取消
        while (true) {
            log("运行中……")
            yield()
        }
    }

    job.cancel()
    Thread.sleep(2000)
}

// CompletionHandler 注册
fun m6() {
    val job = GlobalScope.launch {
        delay(1000)
    }

    job.cancel()
    job.invokeOnCompletion { cause: Throwable? ->
        when (cause) {
            null -> log("协程正常完成 ✅")
            is CancellationException -> log("协程被取消了 🛑")
            else -> log("协程发生异常: ${cause.message} ⚠️")
        }
    }

    Thread.sleep(2000)
    log("job isCancelled: ${job.isCancelled} , job isCompleted: ${job.isCompleted}")
}

// JobSupport 内部异常测试，CancellationException异常特殊，会被认为是正常取消，不向上抛出异常
fun m7() {
    GlobalScope.launch(CoroutineExceptionHandler { _, throwable ->
        log(throwable)
    }) {
        launch {
            log(1)
            delay(50)
            // CancellationException 达到父Job，不会继续向上抛出异常，内部就处理了
            throw CancellationException("主动取消")
            // 其它异常都会往上抛出，被 CoroutineExceptionHandler 接收
//                throw NullPointerException("空指针")
        }

        launch {
            delay(100)
            log(2)
        }

    }
    Thread.sleep(1000)
}