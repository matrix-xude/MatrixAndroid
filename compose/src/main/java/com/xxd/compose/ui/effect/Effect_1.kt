package com.xxd.compose.ui.effect

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.xxd.compose.R


/**
 * 研究各种附带效应 Effect
 */
@Composable
fun Effect1() {

    var key by remember {
        mutableIntStateOf(0)
    }

    Column(modifier = Modifier.width(200.dp).height(300.dp).background(color = Color(0xFF00FF00))) {

        Text(
            modifier = Modifier
                .size(100.dp, 150.dp)
                .background(color = Color(0x5500FFFF), shape = RoundedCornerShape(20))
                .padding(10.dp)
                .clickable { key++ }
                .apply { Log.d("xxd-effect", "trace Text-Modifier Effect之前") }, // 检测 LaunchedEffect
            text = "我在Effect之前",
            overflow = TextOverflow.Ellipsis,
            color = colorResource(id = R.color.purple_700),
        ).apply { Log.d("xxd-effect", "trace Text Effect之前") }

        // 这些代码放在Text之前执行的也执行的比Text晚，可能是Effect中开启了协程的原因
        LaunchedEffect(key1 = key) {
            Log.d("xxd-effect", "trace LaunchedEffect : key1=$key")
        }

        DisposableEffect(key1 = key) {
            Log.d("xxd-effect", "trace DisposableEffect : key1=$key")

            onDispose {
                Log.d("xxd-effect", "trace DisposableEffect onDispose : key1=$key")
            }
        }

        Text(
            modifier = Modifier
            .size(100.dp, 150.dp)
                .background(color = Color(0x5500FFFF), shape = RoundedCornerShape(20))
                .padding(10.dp)
                .clickable { key++ }
                .apply { Log.d("xxd-effect", "trace Text-Modifier Effect之后") }, // 检测 LaunchedEffect
            text = "我在Effect之后",
            overflow = TextOverflow.Ellipsis,
            color = colorResource(id = R.color.purple_700),
        ).apply { Log.d("xxd-effect", "trace Text Effect之后") }
    }
}