package com.xxd.compose.activitys

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.whenStateAtLeast
import com.xxd.common.util.toast.ToastUtil
import com.xxd.compose.R
import com.xxd.compose.data.FakeData
import com.xxd.compose.ui.lazy.FirstList
import com.xxd.compose.ui.state.ExpandedText
import com.xxd.compose.ui.state.Foo
import com.xxd.compose.ui.state.RememberMain
import com.xxd.compose.ui.state.SecondMain
import com.xxd.compose.ui.theme.MatrixAndroidTheme
import com.xxd.compose.viewmodel.SecondViewModel
import kotlinx.coroutines.launch

/**
 *    author : xxd
 *    date   : 2024/3/6
 *    desc   :
 */
class SecondActivity : ComponentActivity() {

    private val viewModel by viewModels<SecondViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MatrixAndroidTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SecondMain(viewModel)
//                    Foo()
                }
            }
        }
        initData()
    }

    private fun initData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    // 使用View收集数据
                    Log.d("xxd2", "使用View收集数据改变 ${it.expanded}")
                }
            }
        }
    }
}