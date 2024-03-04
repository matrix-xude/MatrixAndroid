package com.xxd.compose.ui.basic

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xxd.compose.R
import com.xxd.compose.data.FakeData
import com.xxd.compose.ui.theme.MatrixAndroidTheme

/**
 *    author : xxd
 *    date   : 2024/1/30
 *    desc   : 类似于Android传统ViewGroup的基础组件
 */

/**
 * Row 是横向排列，x轴方向
 */
@Composable
fun RowSimple(nameList: List<String>) {
    Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom, modifier = Modifier.fillMaxWidth()) {
        Icon(tint = Color.Red, painter = painterResource(id = R.drawable.facebook), contentDescription = "")
        nameList.forEach {
            Text(text = it)
        }
    }
}

/**
 * Column 是纵向排列，y轴方向
 */
@Composable
fun ColumnSimple(nameList: List<String>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        nameList.forEach {
            Text(text = it)
        }
    }
}

/**
 * Box 是相对排列
 */
@Composable
fun BoxSimple(nameList: List<String>) {
    Box {
        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "")
        Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "", tint = Color.Red)
//        nameList.forEach {
//            Text(text = it)
//        }
    }
}

@Composable
fun UseBoxWithConstraints() {
    BoxWithConstraints {
        Log.d("xxd2", "minWidth=$minWidth,maxWidth=$maxWidth,minHeight=$minHeight,maxHeight=$maxHeight")
        if (maxWidth > 300.dp) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info_outline_white_48dp), contentDescription = "", tint = Color.Cyan
                )
                Text(text = "高端大气上档次", modifier = Modifier.padding(start = 10.dp))
            }
        } else {
            Column(horizontalAlignment = Alignment.Start) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info_outline_white_48dp), contentDescription = "", tint = Color.Red
                )
                Text(text = "高端大气上档次", modifier = Modifier.padding(top = 10.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RowTest() {
    val shotStrList = FakeData.fakeShotStrList(3)
//    val shotStrList = listOf("123","222","323")
    MatrixAndroidTheme {
        RowSimple(nameList = shotStrList)
    }
}

@Preview(showBackground = true)
@Composable
fun ColumnTest() {
    val shotStrList = FakeData.fakeShotStrList(11)
    MatrixAndroidTheme {
        ColumnSimple(nameList = shotStrList)
    }
}

@Preview(showBackground = true)
@Composable
fun BoxTest() {
    val shotStrList = FakeData.fakeShotStrList(4)
    MatrixAndroidTheme {
        BoxSimple(nameList = shotStrList)
    }
}

@Preview(showBackground = true)
@Composable
fun UseBoxWithConstraintsTest() {
    MatrixAndroidTheme {
        Box(modifier = Modifier.width(401.dp)) {
            UseBoxWithConstraints()
        }
    }
}