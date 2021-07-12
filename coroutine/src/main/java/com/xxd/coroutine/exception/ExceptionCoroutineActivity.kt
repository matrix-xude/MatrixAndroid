package com.xxd.coroutine.exception

import android.app.ProgressDialog
import android.view.ViewGroup
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.extend.onClick
import com.xxd.coroutine.databinding.CoroutineActivityExceptionBinding
import com.xxd.coroutine.myself.MyInterceptor
import com.xxd.coroutine.utils.log
import com.xxd.coroutine.utils.log2
import kotlinx.coroutines.*
import java.lang.RuntimeException

/**
 * author : xxd
 * date   : 2021/7/11
 * desc   : 测试携程的异常机制
 */
class ExceptionCoroutineActivity : BaseTitleActivity() {

    private lateinit var viewBinding: CoroutineActivityExceptionBinding

    private val dialog by lazy {
        ProgressDialog(this)
    }

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = CoroutineActivityExceptionBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence {
        return "携程异常传递机制"
    }

    override fun initView() {
        super.initView()
        viewBinding.tv1.onClick { m1() }
        viewBinding.tv2.onClick { m2() }
        viewBinding.tv3.onClick { m3() }
        viewBinding.tv4.onClick { m4() }
        viewBinding.tv5.onClick { m5() }
        viewBinding.tv6.onClick { m6() }
        viewBinding.tv7.onClick { m7() }
        viewBinding.tv8.onClick { m8() }
        viewBinding.tv9.onClick { m9() }
        viewBinding.tv10.onClick { m10() }
    }

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            log2("handler捕获了异常：${throwable.message}", coroutineContext)
        }

    // try catch 会捕获异常，不会传到 coroutineExceptionHandler
    private fun m1() {
        GlobalScope.launch(Dispatchers.Main + CoroutineName("1") + coroutineExceptionHandler) {
            try {
                delay(10)
                throw RuntimeException("异常-1")
            } catch (e: Exception) {
                log(e, this)
            }
        }
    }

    private fun m2() {
        GlobalScope.launch(Dispatchers.Main + CoroutineName("2") + coroutineExceptionHandler) {
            delay(10)
            throw RuntimeException("异常-2")
        }
    }

    private fun m3() {
        GlobalScope.launch(MyInterceptor()+CoroutineName("1"),start = CoroutineStart.DEFAULT) {


            log(1, this)
            launch (CoroutineName("2"),){
                delay(2)
                log(2, this)
            }.join()
            log(3, this)
        }
    }

    private fun m4() {
        GlobalScope.launch(MyInterceptor()+CoroutineName("1"), start = CoroutineStart.DEFAULT) {

            log(1, this)
            launch (CoroutineName("2"),){
//                delay(2)
                log(2, this)
            }.join()
            log(3, this)
        }
    }

    // 异常处理
    private fun m5() {
        GlobalScope.launch(Dispatchers.Main + MyInterceptor()) {
            log(1)
            try {
                coroutineScope { //①
                    log(2)
                    launch { // ②
                        log(3)
                        launch { // ③
                            log(4)
                            delay(100)
                            log(222222222)
                            throw ArithmeticException("Hey!!")
                        }
                        log(5)
                    }
                    log(6)
                    val job = launch { // ④
                        log(7)
                        delay(1000)
                    }
                    try {
                        log(8)
                        job.join()
                        log("9")
                    } catch (e: Exception) {
                        log("10. $e")
                    }
                }
                log(11)
            } catch (e: Exception) {
                log("12. $e")
            }
            log(13)
        }
    }

    // 异常处理
    private fun m6() {
        GlobalScope.launch(Dispatchers.Main + MyInterceptor()) {
            log(1)
            try {
//                coroutineScope { //①
                log(2)
                launch { // ②
                    log(3)
                    launch { // ③
                        log(4)
                        delay(100)
                        log(222222222)
                        throw ArithmeticException("Hey!!")
                    }
                    log(5)
                }
                log(6)
                val job = launch { // ④
                    log(7)
                    delay(1000)
                }
                try {
                    log(8)
                    job.join()
                    log("9")
                } catch (e: Exception) {
                    log("10. $e")
                }
//                }
                log(11)
            } catch (e: Exception) {
                log("12. $e")
            }
            log(13)
        }
    }

    private fun m7() {
        GlobalScope.launch(MyInterceptor()) {
            val job = this.coroutineContext[Job]
            log(job!!)
            try {
                launch {
                    val job = this.coroutineContext[Job]
                    log(job!!)
                    delay(10)
                    throw ArithmeticException("Hey!!")
                }

                launch {
                    delay(100)
                    log(1)
                }
            } catch (e: Exception) {
                log(e.message!!)
            }
        }
    }

    private fun m8() {
        GlobalScope.launch(MyInterceptor()) {
            try {
                val job = this.coroutineContext[Job]
                log(job!!)
                coroutineScope {
                    val job = this.coroutineContext[Job]
                    log(job!!)
                    launch {
                        val job = this.coroutineContext[Job]
                        log(job!!)
                        delay(10)
                        throw ArithmeticException("Hey!!")
                    }

                    launch {
                        delay(100)
                        log(1)
                    }
                }
            } catch (e: Exception) {
                log(e.message!!)
            }
        }
    }

    private fun m9() {
        GlobalScope.launch(MyInterceptor()
                + CoroutineExceptionHandler { coroutineContext, throwable ->
            log(coroutineContext)
            log(throwable)
        }
        ) {
            val job = this.coroutineContext[Job]
            log(job!!)
            try {
                val job2 = async {
                    val job = this.coroutineContext[Job]
                    log(job!!)
                    delay(10)
                    throw ArithmeticException("Hey!!")
                }
//                val await = job2.await()

                launch {
                    delay(100)
                    log(1)
                }
            } catch (e: Exception) {
                log(e.message!!)
            }
        }
    }

    private fun m10() {
        GlobalScope.launch(MyInterceptor()
                + CoroutineExceptionHandler { coroutineContext, throwable ->
            log(coroutineContext)
            log(throwable)
        }
        ) {
            val job = this.coroutineContext[Job]
            log(job!!)
            try {
                launch {
                    val job = this.coroutineContext[Job]
                    log(job!!)
                    delay(10)
                    throw ArithmeticException("Hey!!")
                }

                var i = 0
                launch {
                    while (true) {
                        delay(1)
                        log("能否停止${i++}")
                    }
                }
            } catch (e: Exception) {
                log(e.message!!)
            }
        }
    }

}

