package com.xxd.compose.ui.basic

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xxd.compose.R
import com.xxd.compose.data.FakeData
import com.xxd.compose.ui.theme.MatrixAndroidTheme

/**
 *    author : xxd
 *    date   : 2024/1/31
 *    desc   : Modifier的基本使用
 */

@Composable
fun ModifierSimple(onClick: () -> Unit) {
    val padding = 15.dp
    Column(
        Modifier
            .clickable(enabled = true, onClickLabel = "ddddd", onClick = onClick)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        Log.d("xxd", "offset=$it , 长按")
                    }
                )
            }
            .background(Color(0x66FF00FF), RoundedCornerShape(15.dp, 5.dp, 10.dp, 25.dp))
            .fillMaxWidth()
            .height(300.dp)
            .padding(padding)
    ) {
        Text(text = FakeData.fakeSimpleString(), color = Color(255, 255, 0, 255))
        Text(text = FakeData.fakeSimpleString(), modifier = Modifier.padding(top = 10.dp))
        Text(
            text = "YMCA", color = Color(0xFFFF0000), modifier = Modifier
                .fillMaxWidth(0.5f)
//                .graphicsLayer( rotationX = 0f , rotationY = 0f, translationX = 200f, shape = RoundedCornerShape(10.dp), clip = true)
                .border(2.dp, Color.Black, RoundedCornerShape(5.dp))
                .padding(2.dp)
                .border(2.dp, Color.Yellow, RoundedCornerShape(5.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Cyan)
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "",
            Modifier
                .background(color = Color(0x330000FF), RoundedCornerShape(20.dp))
                .size(150.dp, 100.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun ModifierSimple2(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .requiredHeight(400.dp)
//        .wrapContentSize(align = Alignment.BottomEnd)
            .background(Color(0x330000FF))
    ) {
        Text(text = "fafdfd")
        Box() {
            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color(0x33FF00FF), RoundedCornerShape(15.dp, 5.dp, 10.dp, 25.dp))
            )
            Row(
                modifier = Modifier
                    .clickable(enabled = false, onClick = onClick)
//                    .background(Color(0x330000FF))
                    .padding(5.dp),
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "",
                    Modifier
                        .size(120.dp, 150.dp)
//                .requiredSize(140.dp, 350.dp)
//                .wrapContentSize(align = Alignment.BottomEnd,unbounded = false)
                        .paddingFromBaseline(100.dp)
                        .background(color = Color(0xFF0000FF), RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "高级高级高高级高级高高级高级高高级高级高", color = Color(255, 0, 0, 255),
                    modifier = Modifier
                        .size(50.dp, 100.dp)
                        .wrapContentSize(align = Alignment.TopStart, unbounded = false)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                ) {
                    Text(
                        text = "姓名", color = Color(255, 255, 0, 255),
                        modifier = Modifier.weight(2f)
                    )
                    Text(text = "日期：1999", modifier = Modifier
                        .absoluteOffset(x = -13.dp, y = 5.dp)
                        .weight(1f, true)
                        .clickable { })
                }
            }
        }
    }
}

@Composable
fun ModifierSimple3(onClick: () -> Unit) {
    Box {
        Row(
            modifier = Modifier
                .clickable(enabled = false, onClick = onClick)
                .background(Color(0x330000FF))
                .padding(0.dp),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "",
                Modifier
                    .size(100.dp, 100.dp)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier.sizeIn(100.dp, 150.dp, 150.dp, 200.dp)
            ) {
                Text(
//                    modifier = Modifier.sizeIn(100.dp,150.dp,150.dp,200.dp,),
                    text = "姓名", color = Color(255, 255, 0, 255),
                )
                Text(text = "日期：1999,日期：1999,日期：1999,日期：1999,", color = Color.Red, modifier = Modifier
                    .clickable { })
            }
        }
    }
}

@Preview
@Composable
fun TestModifierSimple() {
    MatrixAndroidTheme {
        ModifierSimple {
            Log.d("xxd", "我被点击了")
        }
    }
}

//@Preview
@Composable
fun TestModifierSimple2() {
    MatrixAndroidTheme {
        ModifierSimple2 {
            Log.d("xxd", "我被点击了")
        }
    }
}

//@Preview
@Composable
fun TestModifierSimple3() {
    MatrixAndroidTheme {
        ModifierSimple3 {
            Log.d("xxd", "我被点击了")
        }
    }
}