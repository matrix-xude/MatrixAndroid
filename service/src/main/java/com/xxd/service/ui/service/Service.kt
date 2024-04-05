package com.xxd.service.ui.service

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Configuration
import android.os.IBinder
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orhanobut.logger.Logger
import com.xxd.common.util.toast.ToastUtil
import com.xxd.service.domain.FunctionItem
import com.xxd.service.service.FirstService
import com.xxd.service.ui.local.LocalColor
import com.xxd.service.ui.theme.ServiceTheme
import com.xxd.service.ui.util.ColorUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *    author : xxd
 *    date   : 2024/4/5
 *    desc   : Service 界面
 */

@Composable
fun ServiceScreen() {
    val myColor = LocalColor.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = myColor.background)
            .systemBarsPadding()  // 留出距离系统bar的padding
    ) {
        ServiceTitle()
        ServiceAction()
    }
}

@Composable
private fun ServiceTitle() {
    val myColor = LocalColor.current
    Text(
        text = "Service生命周期", color = myColor.title, fontSize = 20.sp, modifier = Modifier
            .fillMaxWidth()
            .background(myColor.tertiary)
            .padding(10.dp)
            .wrapContentSize(align = Alignment.Center)
    )
}

@Composable
private fun ServiceAction() {
    val myColor = LocalColor.current
    val context = LocalContext.current

    var serviceConnection : ServiceConnection? = null
    LaunchedEffect(key1 = true) {
         serviceConnection= object : ServiceConnection{
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                // service 不为null才会回调，并且同一个 serviceConnection 只会回调一次
                Logger.d("onServiceConnected className=${name?.className},packageName=${name?.packageName}")
                if (service is FirstService.FirstBinder){
                    service.getService().logFirstService()
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                // 一般不会走到
                Logger.d("onServiceDisconnected className=${name?.className},packageName=${name?.packageName}")
            }

        }
    }

    val items by remember {
        val list = mutableListOf<FunctionItem>()
        repeat(20) {
            val item = when (it) {
                0 -> FunctionItem(it + 1, "startService")
                1 -> FunctionItem(it + 1, "stopService")
                2 -> FunctionItem(it + 1, "bindService")
                3 -> FunctionItem(it + 1, "unBindService")
                else -> FunctionItem(it + 1, "功能${it + 1}")
            }
            list.add(item)
        }
        mutableStateOf(list)
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(color = myColor.backgroundSecond)
            .padding(8.dp)
    ) {
        items(items = items, key = { it.id }) {
            ServiceActionItem(it) { id ->
                when (id) {
                    1 -> {
                        val componentName = context.startService(Intent(context, FirstService::class.java))
//                        Logger.d("start服务返回值 className=${componentName?.className},packageName=${componentName?.packageName}")
                    }
                    2 -> {
                        val stopService = context.stopService(Intent(context, FirstService::class.java))
//                        Logger.d("stop服务返回值 $stopService")
                    }
                    3 -> {
                        val intent = Intent(context, FirstService::class.java)
                        // Service.BIND_AUTO_CREATE 使用后才会创建Service，否则无效！
                        // 同一个 serviceConnection 只会生效1次
                        val bindService = context.bindService(intent, serviceConnection!!, Service.BIND_AUTO_CREATE)
                    }
                    4 -> {
                        // 并且同一个 serviceConnection 多次调用会抛出异常
                        context.unbindService(serviceConnection!!)
                    }
                }
            }
        }
    }
}

@Composable
private fun ServiceActionItem(item: FunctionItem, onClick: (Int) -> Unit) {
    Text(
        text = item.name, color = Color.Black, fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ColorUtil.randomColor(), shape = RoundedCornerShape(10.dp))
            .clickable { onClick.invoke(item.id) }
            .padding(10.dp)
            .wrapContentSize(align = Alignment.Center)
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreScreen() {
    ServiceTheme {
        ServiceScreen()
    }
}