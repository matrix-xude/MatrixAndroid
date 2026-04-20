package com.xxd.compose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.xxd.compose.ui.state.domain.Level1
import com.xxd.compose.ui.state.domain.Level2
import com.xxd.compose.ui.state.domain.Level2Object
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 *    author : xxd
 *    date   : 2024/3/5
 *    desc   :
 */
class SecondViewModel : ViewModel() {

    // stateFlow创建数据源
    private val _uiState = MutableStateFlow(
        (Level1(
            name = "高大上",
            age = 18,
            level2 = Level2(score = 99.9f, desc = "满分只有100，你确只能考99"),
            level2Object = Level2Object(33, "do it")
        ))
    )
    val uiState = _uiState.asStateFlow()

    // State创建数据源
    var uiState2 = mutableStateOf(
        Level1(
            name = "高大上",
            age = 18,
            level2 = Level2(score = 99.9f, desc = "满分只有100，你确只能考99"),
            level2Object = Level2Object(33, "do it")
        )
    )
        private set

    fun changeExpanded() {
        // update方法线程安全
        uiState2.value = uiState2.value.run {
            copy(age = age + 1)
        }

    }
}
