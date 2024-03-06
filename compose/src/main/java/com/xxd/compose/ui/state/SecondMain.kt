package com.xxd.compose.ui.state

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xxd.compose.data.SecondUIState
import com.xxd.compose.viewmodel.SecondViewModel

/**
 *    author : xxd
 *    date   : 2024/3/5
 *    desc   :
 */
@Composable
fun SecondMain(viewModel: SecondViewModel) {
    // 是否展开
    val uiStateState = viewModel.uiState.collectAsStateWithLifecycle()
//    val uiState2 = remember {
//        viewModel.uiState2
//    }

    Column(modifier = Modifier.padding(vertical = 5.dp)) {
        ExpandedText(name = "Hello\n" + "World", { uiStateState.value.expanded }) { viewModel.changeExpanded() }
        ExpandedText(name = "Hello\n" + "Compose", { uiStateState.value.expanded }) { viewModel.changeExpanded() }
    }
}

@Composable
fun ExpandedText(name: String, expanded: () -> Boolean, click: () -> Unit) {


    Log.d("xxd2", "高级")

//    val paddingBottom = if (expanded()) 48.dp else 24.dp

    Surface(color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp).also { Log.d("xxd2", "Surface重组了") }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 24.dp)
                .also { Log.d("xxd2", "Row重组了") },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Wrapper2 {
                Text(
                    text = "123", fontSize = 16.sp, modifier = Modifier
                        .weight(1f)
                        .padding(start = if (expanded()) 48.dp else 24.dp)
                ).also { Log.d("xxd2", "Text重组了") }

            }
            ElevatedButton(onClick = click) {
                Log.d("xxd2", "ElevatedButton content重组了")
                Wrapper2() {
                    Text(text = if (expanded()) "Show less" else "Show more").also { Log.d("xxd2", "ElevatedButton重组了") }
                }
            }
        }
    }
}

@Composable
fun Wrapper2(content: @Composable () -> Unit) {
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