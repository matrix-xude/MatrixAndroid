package com.xxd.compose.ui.state

import android.annotation.SuppressLint
import android.util.Log
import android.view.WindowInsets.Side
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.orhanobut.logger.Logger
import com.xxd.compose.data.SecondUIState
import com.xxd.compose.util.ColorUtil
import com.xxd.compose.viewmodel.SecondViewModel

/**
 *    author : xxd
 *    date   : 2024/3/5
 *    desc   :
 */
@Composable
fun SecondMain(viewModel: SecondViewModel) {
    // 是否展开
    val uiStateState by viewModel.uiState.collectAsStateWithLifecycle()
//    val uiState2 = remember {
//        viewModel.uiState2
//    }
//    val uiStateState = viewModel.uiState2

    SideEffect {
        Logger.d("SecondMain中的SideEffect触发")
    }

    Column(modifier = Modifier.padding(vertical = 5.dp)) {
        ExpandedText(name = "Hello\n" + "World",  uiStateState) { viewModel.changeExpanded() }
        ExpandedText(name = "Hello\n" + "Compose", uiStateState) { viewModel.changeExpanded() }
    }
}

@Composable
fun ExpandedText(name: String, expanded: () -> Boolean, click: () -> Unit) {

    SideEffect {
        Logger.d("ExpandedText 中的SideEffect触发")
    }

    Log.d("xxd2", "高级")

//    val paddingBottom = if (expanded()) 48.dp else 24.dp

    Surface(color = MaterialTheme.colorScheme.primary, modifier = Modifier
        .padding(vertical = 4.dp, horizontal = 8.dp)
        .background(ColorUtil.randomColor())
        .also { Log.d("xxd2", "Surface参数重组了") }) {

        SideEffect {
            Logger.d("Surface 中的SideEffect触发")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 24.dp)
                .also { Log.d("xxd2", "Row重组了") },
            verticalAlignment = Alignment.CenterVertically
        ) {

            SideEffect {
                Logger.d("Row 中的SideEffect触发")
            }

            Wrapper2 {
                SideEffect {
                    Logger.d("Wrapper2 SideEffect触发")
                }
                Log.d("xxd2", "Wrapper2 外部重组了222")
                Text(
                    text = name, fontSize = 16.sp, modifier = Modifier
                        .weight(1f)
                        .padding(start = 24.dp)
                ).also { Log.d("xxd2", "Text重组了") }

            }
            ElevatedButton(onClick = click) {
                SideEffect {
                    Logger.d("ElevatedButton 方法中的SideEffect触发")
                }
                Log.d("xxd2", "ElevatedButton content重组了")
                Wrapper2() {
                    SideEffect {
                        Logger.d("Wrapper2中后一个 SideEffect触发")
                    }
                    Text(text = if (expanded()) "Show less" else "Show more").also { Log.d("xxd2", "ElevatedButton重组了") }
                }
            }.apply { Logger.d("ElevatedButton 真实发生了 ~~~ 重组") }
        }
    }.also { Log.d("xxd2", "Surface重组了") }
}

@Composable
fun MyText(name:String){
    Text(
        text = name, fontSize = 16.sp, modifier = Modifier
            .padding(start = 48.dp)
            .background(ColorUtil.randomColor())
    ).also { Log.d("xxd2", "Text重组了") }
}
@Composable
fun ExpandedText(name: String, secondUIState: SecondUIState, click: () -> Unit) {

    SideEffect {
        Logger.d("ExpandedText 中的SideEffect触发")
    }

    Log.d("xxd2", "高级")

//    val paddingBottom = if (expanded()) 48.dp else 24.dp

    Surface(color = MaterialTheme.colorScheme.primary, modifier = Modifier
        .padding(vertical = 4.dp, horizontal = 8.dp)
        .background(ColorUtil.randomColor())
        .also { Log.d("xxd2", "Surface参数1重组了") }) {

        SideEffect {
            Logger.d("Surface 中的SideEffect触发")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 24.dp)
                .background(ColorUtil.randomColor())
                .also { Log.d("xxd2", "Row重组了") },
            verticalAlignment = Alignment.CenterVertically
        ) {

            SideEffect {
                Logger.d("Row 中的SideEffect触发")
            }

            Wrapper2 {
                SideEffect {
                    Logger.d("Wrapper2 SideEffect触发")
                }
                Log.d("xxd2", "Wrapper2 外部重组了222")
                
                Text(text = if (secondUIState.expanded)"aaa" else "bbb")
                
                MyText(name = name)
            }
            ElevatedButton(onClick = click, modifier = Modifier.background(ColorUtil.randomColor())) {
//                SideEffect {
//                    Logger.d("ElevatedButton 方法中的SideEffect触发")
//                }
                Log.d("xxd2", "ElevatedButton content重组了")
                Wrapper2() {
                    SideEffect {
                        Logger.d("Wrapper2中后一个 SideEffect触发")
                    }
                    Text(text =  "Show less" , modifier = Modifier.background(ColorUtil.randomColor())).also { Log.d("xxd2", "ElevatedButton重组了") }
                }
            }.apply { Logger.d("ElevatedButton 真实发生了 ~~~ 重组") }
        }
    }
}

@Composable
fun Wrapper2(content: @Composable () -> Unit) {
    SideEffect {
        Logger.d("Wrapper2 方法中的SideEffect触发")
    }
    Log.d("xxd2", "Wrapper recomposing")
    Box {
        Log.d("xxd2", "Box")
        content()
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ExpandedText(name: String) {


    // 是否展开
    val expanded = remember {
        Log.d("xxd2", "remember")
        mutableStateOf(false)
    }
    val paddingBottom = if (expanded.value) 48.dp else 24.dp
    Log.d("xxd2", "高级2")

    Surface(color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = paddingBottom),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name, fontSize = 16.sp, modifier = Modifier.weight(1f))
            ElevatedButton(onClick = { expanded.value = !expanded.value }) {
                Text(text = if (expanded.value) "Show less" else "Show more")
            }

//            LaunchedEffect(expanded){
//
//            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TestExpandedText() {
    ExpandedText("Hello\r\nworld")
}