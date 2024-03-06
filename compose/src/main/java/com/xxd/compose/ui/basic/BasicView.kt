package com.xxd.compose.ui.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import com.xxd.common.util.toast.ToastUtil
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
 *    4. Spacer
 *    5. Button
 *    6. Divider
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonSimple() {
    val context = LocalContext.current
    Column {

        Badge(modifier = Modifier.wrapContentSize(), contentColor = Color.Cyan) {
            Text(text = "999+", fontSize = 12.sp, modifier = Modifier.padding(2.dp))
        }
        Button(
            enabled = true,
            onClick = { ToastUtil.showToast(context, "12223") },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00FF00), contentColor = Color(0xFF0000FF),
                disabledContainerColor = Color(0x22000000), disabledContentColor = Color.Black
            ),
            modifier = Modifier.background(Color.Magenta)
        ) {
            Text(text = "稀奇")
            Icon(painter = painterResource(id = R.drawable.ic_info_outline_white_48dp), contentDescription = "")
        }

        Spacer(
            modifier = Modifier
                .size(10.dp)
                .background(Color.Yellow)
        )
        Divider(
            thickness = 2.dp,
            color = Color.Red,
            modifier = Modifier
                .width(50.dp)
        )

        OutlinedButton(
            onClick = {
                ToastUtil.showToast(context, "123")
            }, shape = RoundedCornerShape(10.dp),
            modifier = Modifier.background(Color.Cyan)
        ) {

        }
    }
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

@Preview(showBackground = true)
@Composable
fun ButtonTest() {
    MatrixAndroidTheme {
        ButtonSimple()
    }
}
