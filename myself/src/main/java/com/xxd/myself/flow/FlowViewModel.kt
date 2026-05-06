package com.xxd.myself.flow

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

    val uiLiveData by lazy {
        MutableLiveData(0)
    }

    fun m1(){
        uiState.update {
            it.copy()
        }
    }

    fun m2(){
        viewModelScope.launch {
            uiShireFlow.emit(1)
        }
    }

    fun m3(): Flow<Int>{
        return callbackFlow {
            send(1)
        }
    }



}
