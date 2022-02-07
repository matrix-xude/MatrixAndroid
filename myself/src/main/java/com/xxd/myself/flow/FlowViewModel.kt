package com.xxd.myself.flow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/**
 *    author : xxd
 *    date   : 2022/2/7
 *    desc   :
 */
class FlowViewModel : ViewModel() {

    val flow by lazy {
        flow {
            emit(1)
            delay(1000)
            emit(2)
            delay(1500)
            emit(3)
        }
    }

    val uiState by lazy {
        MutableStateFlow(AAA(1,"张三"))
    }

    val uiShireFlow by lazy {
        MutableSharedFlow<Int>(2)
    }
}