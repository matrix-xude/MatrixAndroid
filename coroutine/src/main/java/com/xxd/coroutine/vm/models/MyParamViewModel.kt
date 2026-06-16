package com.xxd.coroutine.vm.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

/**
 *    author : xxd
 *    date   : 2026/5/25
 *    desc   : 带有自定义参数的ViewModel
 */
class MyParamViewModel(private val desc : String, private val count : Int) : ViewModel() {

    fun testCall() {
        println("MyParamViewModel: testCall invoked, desc: $desc, count: $count")
    }

    companion object {
        /**
         * 定义两个 Key，用于在 CreationExtras 中存取参数
         */
        val DESC_KEY = object : CreationExtras.Key<String> {}
        val DESC_KEY2 : CreationExtras.Key<String> = CreationExtras.Companion.Key() // 还可以使用该方法创建 Key
        val COUNT_KEY = object : CreationExtras.Key<Int> {}

        /**
         * 提供一个通用的 Factory
         */
        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // 从 extras 中取出参数
                val desc = extras[DESC_KEY] ?: "默认描述"
                val count = extras[COUNT_KEY] ?: 0
                return MyParamViewModel(desc, count) as T
            }
        }
    }
}