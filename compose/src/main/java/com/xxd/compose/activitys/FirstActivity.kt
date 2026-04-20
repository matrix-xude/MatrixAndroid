package com.xxd.compose.activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import com.xxd.common.util.toast.ToastUtil
import com.xxd.compose.R
import com.xxd.compose.data.FakeData
import com.xxd.compose.ui.lazy.FirstList
import com.xxd.compose.ui.theme.MatrixAndroidTheme

/**
 *    author : xxd
 *    date   : 2024/3/5
 *    desc   : 测试基础控件
 */
class FirstActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MatrixAndroidTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    FirstList(list = FakeData.fakeFirstList())
                }
            }
        }
    }
}
