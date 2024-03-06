package com.xxd.compose.ui.state

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xxd.compose.data.FirstItem

/**
 *    author : xxd
 *    date   : 2024/3/6
 *    desc   : 探寻remember到底做了什么
 *
 *    remember 是 Jetpack Compose 中的一个核心函数，它用于记住那些你不希望在重组（recomposition）时重新创建的数据。举个例子，这可能是一种状态、一个对象实例或一个计算成本较高的结果。它有助于保持性能并避免不必要的计算。
 */

@Composable
fun RememberContentView(index: Int) {

    val firstItemMutableState by remember {
        mutableStateOf(FirstItem(name = "Ryan", desc = "A man chooses, A slave obeys")).apply {
            Log.d("xxd2", "ContentView is remember $this")
        }
    }

    // 不使用remember
//    val firstItemMutableState = run {
//        mutableStateOf(FirstItem(name = "Ryan", desc = "A man chooses, A slave obeys")).apply {
//            Log.d("xxd2", "ContentView is remember $this")
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .border(1.dp, Color.Cyan, RoundedCornerShape(10.dp))
            .padding(5.dp)
    ) {
        Text(text = "this is pager $index, item = $firstItemMutableState")
    }
}

@Composable
fun TabButton(index: Int, onClick: (Int) -> Unit) {
    OutlinedButton(onClick = { onClick(index) }) {
        Text(text = "Tab $index")
    }
}

@Composable
fun RememberMain() {
//    var state by remember {
//        mutableIntStateOf(0)
//    }

    var state = 0
    Log.d("xxd2", "RememberMain : recomposed")

    Column {
        Row {
            repeat(3) {
                TabButton(index = it, onClick = { index ->
                    state = index
                    Log.d("xxd2", "onClick : tab_$index")
                })
            }
        }
        RememberContentView(index = state)
//        when (state) {
//
//            0 -> {
//                Log.d("xxd2", "state0")
//                RememberContentView (0)
//            }
//
//            1 -> RememberContentView(1)
//            else -> RememberContentView(2)
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextRememberMain() {
    RememberMain()
}



