package com.xxd.coroutine.vm.models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

/**
 *    author : xxd
 *    date   : 2026/5/25
 *    desc   : 带有 SavedStateHandle 参数的 ViewModel
 */
class SavedStateViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    // 方式 1：StateFlow（推荐给 UI 监听）
    val nameState: StateFlow<String> = savedStateHandle.getStateFlow("name", "Default")

    fun testCall() {
        // 方式 2：直接 get（只拿当前那一瞬间的值）
        val age = savedStateHandle.get<Int>("age") ?: 0

        println("SavedStateViewModel: testCall invoked, name: ${nameState.value}, age: $age")

        // 如果此后执行了：
        // savedStateHandle["age"] = 25
        // 那么上面变量 age 依然是 0，而 nameState 则会发射新值。
    }
}