package com.xxd.coroutine.vm.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxd.coroutine.utils.logCoroutine
import kotlinx.coroutines.launch

/**
 *    author : xxd
 *    date   : 2026/5/25
 *    desc   : 无参ViewModel
 */
class NoParamViewModel : ViewModel() {

    init {
        // 实例化并将其添加到 ViewModel 的自动释放队列中
        addCloseable(MyClose())
    }


    fun testCall() {
        println("NoParamViewModel: testCall invoked")
    }

    fun m1(){
        // viewModelScope 的 CoroutineContext 为 Dispatchers.Main.immediate + SupervisorJob
        viewModelScope.launch {
            logCoroutine("m1")
        }
    }

    // 展示如何在 ViewModel 中使用 AutoCloseable 接口，作用是在 ViewModel 被销毁时自动释放资源，需要手动添加到释放队列中 addCloseable
    inner class MyClose : AutoCloseable {
        override fun close() {
            println("MyClose: close")
        }
    }
}