package com.xxd.compose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.xxd.compose.data.SecondUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 *    author : xxd
 *    date   : 2024/3/5
 *    desc   :
 */
class SecondViewModel : ViewModel() {

    // stateFlow创建数据源
    private val _uiState = MutableStateFlow(SecondUIState())
    val uiState = _uiState.asStateFlow()

    // State创建数据源
    var uiState2 by mutableStateOf(SecondUIState(expanded = true))
        private set

    fun changeExpanded() {
        // update方法线程安全
        _uiState.update {
            it.copy(expanded = !it.expanded)
        }
//        _uiState.value = _uiState.value.let {
//            it.copy(expanded = !it.expanded)
//        }

        uiState2 = uiState2.let {
            it.copy(expanded = !it.expanded)
        }

    }
}