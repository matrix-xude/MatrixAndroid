package com.xxd.compose.ui.state

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.xxd.compose.data.FirstItem

/**
 *    author : xxd
 *    date   : 2024/3/6
 *    desc   :
 */
@Composable
fun Foo() {
//    var text by remember { mutableStateOf("高大上") }
    var text by remember { mutableStateOf(FirstItem(name = "123", desc = "高大上")) }
    Log.d("xxd2", "Foo")
    Box {
        Button(onClick = { text = text.copy(desc = "${text.desc} ${text.desc}") }
            .also { Log.d("xxd2", "Button $it") }
        ) {
            Log.d("xxd2", "Button content lambda")
            Wrapper {

                Text(text.desc).also {
                    Log.d("xxd2", "Text")
                }
            }
        }

    }

}

@Composable
fun Wrapper(content: @Composable () -> Unit) {
    Log.d("xxd2", "Wrapper recomposing")
    Box {
        Log.d("xxd2", "Box")
        content()
    }
}
