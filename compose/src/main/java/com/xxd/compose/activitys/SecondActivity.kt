package com.xxd.compose.activitys

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.xxd.compose.ui.state.SeekComposeFun
import com.xxd.compose.ui.state.SeekStableVM
import com.xxd.compose.ui.theme.MatrixAndroidTheme
import com.xxd.compose.viewmodel.SecondViewModel
import kotlinx.coroutines.launch

/**
 *    author : xxd
 *    date   : 2024/3/6
 *    desc   : 专门测试 compose 重组的 稳定性问题
 */
class SecondActivity : ComponentActivity() {

    private val viewModel by viewModels<SecondViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MatrixAndroidTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
//                   SeekStable()
//                   SeekComposeFun()
                    SeekStableVM(viewModel = viewModel)
                }
            }
        }
        initData()
    }

    private fun initData() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.uiState.collect {
//                    // 使用View收集数据
//                    Log.d("xxd2", "使用View收集数据改变 ${it.expanded}")
//                }
//            }
//        }
    }
}
