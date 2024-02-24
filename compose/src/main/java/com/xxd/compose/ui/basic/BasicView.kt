package com.xxd.compose.ui.basic

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.xxd.compose.R
import com.xxd.compose.data.FakeData
import com.xxd.compose.ui.theme.MatrixAndroidTheme

/**
 *    author : xxd
 *    date   : 2024/1/30
 *    desc   : 类似于Android传统View的基础组件，不是ViewGroup那种
 *
 *    1. Text
 *    2. Image
 *    3. Icon
 */

/**
 * 文字的控件
 */
@Composable
fun TextSimple(name: String) {
    Text(text = name)
}

/**
 * 图片控件
 */
@Composable
fun ImageSimple(painter: Painter) {
    Image(painter = painter, contentDescription = "ImageSimple")
}

/**
 * icon 控件，只能用于单色图
 * 可以控制icon的颜色，大小等，
 */
@Composable
fun IconSimple(painter: Painter, tint: Color) {
    Icon(painter = painter, contentDescription = "IconSimple", tint = tint)
}



@Preview(showBackground = true)
@Composable
fun TextTest() {
    val simpleString = FakeData.fakeSimpleString()
    MatrixAndroidTheme {
        TextSimple(name = simpleString)
    }
}

@Preview(showBackground = true)
@Composable
fun ImageTest() {
    val painter = painterResource(id = R.drawable.ic_launcher_background)
    MatrixAndroidTheme {
        ImageSimple(painter)
    }
}

@Preview(showBackground = true)
@Composable
fun IconTest() {
    val painter = painterResource(id = R.drawable.common_ic_back_white)
    MatrixAndroidTheme {
        IconSimple(painter, Color.Red)
    }
}
