package com.xxd.compose.ui.state

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.xxd.compose.data.FirstItem
import com.xxd.compose.util.ColorUtil

/**
 *    author : xxd
 *    date   : 2024/3/30
 *    desc   : 探索compose重组的触发位置
 */
@Composable
fun SeekComposeFun() {

    var text by remember { mutableStateOf(FirstItem(name = "123", desc = "高大上")) }

    Column(
        modifier = Modifier
            .background(color = ColorUtil.randomColor(), shape = RoundedCornerShape(5.dp))
    ) {
        //
        Button(onClick = {}) {// 重组的范围从这里开始 @Composable RowScope.() -> Unit
            Text(
                text = text.desc,  // 这里读取了 State<T>的数值，所以该 @composable方法会重组
                modifier = Modifier
                    .clickable { text = text.copy(name = "${text.name}1") }
                    .background(color = ColorUtil.randomColor(), shape = RoundedCornerShape(5.dp))
                    .padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.size(10.dp))

        Box(
            modifier = Modifier
                .background(color = ColorUtil.randomColor(), shape = RoundedCornerShape(5.dp))
                .padding(10.dp)
        ) {
            Wrapper {  // 重组的范围从这里开始 content: @Composable () -> Unit
                Text(
                    text = text.desc, // 这里读取了 State<T>的数值，所以该 @composable方法会重组
                    modifier = Modifier
                        .clickable { text = text.copy(name = "${text.name}1") }
                        .background(color = ColorUtil.randomColor(), shape = RoundedCornerShape(5.dp))
                        .padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun Wrapper(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .background(color = ColorUtil.randomColor(), shape = RoundedCornerShape(5.dp))
            .padding(10.dp)
    ) {
        content()
    }
}
















