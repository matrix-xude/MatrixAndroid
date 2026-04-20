package com.xxd.compose.ui.state

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.xxd.compose.ui.state.domain.Level2Object
import com.xxd.compose.util.ColorUtil
import com.xxd.compose.viewmodel.SecondViewModel

/**
 *    author : xxd
 *    date   : 2024/3/29
 *    desc   : 探索 compose重组中 参数的稳定性问题
 */
@Composable
fun SeekStableVM(viewModel: SecondViewModel) {


    // 需要测试的数据
    var level1 by remember { viewModel.uiState2 }

    Column(
        modifier = Modifier
            .width(0.dp)
            .height(100.dp)
            .background(color = ColorUtil.randomColor())
    ) {

        SeekStable11(name = level1.name, age = level1.age, click = {
           viewModel.changeExpanded()
        })
        Spacer(modifier = Modifier.size(10.dp))

        SeekStable21(level2 = level1.level2, click = {
        })
        Spacer(modifier = Modifier.size(10.dp))

        SeekStable31(level2Object = level1.level2Object, click = {
        })
        Spacer(modifier = Modifier.size(10.dp))

        // 直接在该重组范围内调用系统compose，因为传入参数太多，不能保证所有必须参数都稳定，导致该Text无法跳过
        Text(
            text = "不变的，是真心",
            modifier = Modifier
                .clickable(onClick = { })
                .background(color = ColorUtil.randomColor(), shape = RoundedCornerShape(5.dp))
                .padding(10.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))

        // 多加一层，再调用系统compose,保证不会重组
        SeekStable41(click = {})
    }
}

@Composable
fun SeekStable11(name: String, age: Int, click: () -> Unit) {
    Text(
        text = "$name ----- $age",
        modifier = Modifier
            .clickable(onClick = click)
            .background(color = ColorUtil.randomColor(), shape = RoundedCornerShape(5.dp))
            .padding(10.dp)
    )
}

@Composable
fun SeekStable21(level2: Level2, click: () -> Unit) {
    Text(
        text = "${level2.score} ----- ${level2.desc}",
        modifier = Modifier
            .clickable(onClick = click)
            .background(color = ColorUtil.randomColor(), shape = RoundedCornerShape(5.dp))
            .padding(10.dp)
    )
}

@Composable
fun SeekStable31(level2Object: Level2Object,click: () -> Unit) {
    Text(
        text = "${level2Object.i} ----- ${level2Object.str}",
        modifier = Modifier
            .clickable(onClick = click)
            .background(color = ColorUtil.randomColor(), shape = RoundedCornerShape(5.dp))
            .padding(10.dp)
    )
}

@Composable
fun SeekStable41(click: () -> Unit) {
    Text(
        text = "不变的，是真心",
        modifier = Modifier
            .clickable(onClick = click)
            .background(color = ColorUtil.randomColor(), shape = RoundedCornerShape(5.dp))
            .padding(10.dp)
    )
}


















