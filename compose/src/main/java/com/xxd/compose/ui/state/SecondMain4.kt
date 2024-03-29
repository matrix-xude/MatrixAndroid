package com.xxd.compose.ui.state

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.xxd.compose.ui.state.domain.Level1
import com.xxd.compose.ui.state.domain.Level2
import com.xxd.compose.util.ColorUtil

/**
 *    author : xxd
 *    date   : 2024/3/29
 *    desc   : 探索 compose重组中 参数的稳定性问题
 */
@Composable
fun SeekStable() {

    // 需要测试的数据
    var level1 by remember {
        mutableStateOf(Level1(name = "高大上", age = 18, level2 = Level2(score = 99.9f, desc = "满分只有100，你确只能考99")))
    }

    Column(
        modifier = Modifier
            .width(200.dp)
            .background(color = Color(0x33ff9977))
    ) {

        SeekStable1(name = level1.name, age = level1.age, click = {
            level1 = level1.copy(age = level1.age + 1)
        })
        Spacer(modifier = Modifier.size(10.dp))
        SeekStable2(level2 = level1.level2, click = {
            level1 = level1.copy(level2 = level1.level2.copy())
        })
    }
}

@Composable
fun SeekStable1(name: String, age: Int, click: () -> Unit) {
    Text(
        text = "$name ----- $age",
        modifier = Modifier
            .clickable(onClick = click)
            .background(color = ColorUtil.randomColor(), shape = RoundedCornerShape(5.dp))
            .padding(10.dp)
    )
}

@Composable
fun SeekStable2(level2: Level2, click: () -> Unit) {
    Text(
        text = "${level2.score} ----- ${level2.desc}",
        modifier = Modifier
            .clickable(onClick = click)
            .background(color = ColorUtil.randomColor(), shape = RoundedCornerShape(5.dp))
            .padding(10.dp)
    )
}


