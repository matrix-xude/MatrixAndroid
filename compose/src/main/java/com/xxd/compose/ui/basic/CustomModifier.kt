package com.xxd.compose.ui.basic

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.xxd.compose.ui.theme.MatrixAndroidTheme

/**
 *    author : xxd
 *    date   : 2024/3/2
 *    desc   : 自定义Modifier
 */

/**
 * 自定义一个离 FirstBaseline距离的Modifier
 * 主要是为了探寻自定义的过程
 */
@Composable
fun Modifier.customFirstBaselinePadding(firstBaseLineToTop: Dp): Modifier {

    // layout 就是快速创造 [LayoutModifier] 的方法
    return layout { measurable, constraints ->
        /**
         * 1.测量compose，返回结果
         * measurable : 计算调用（如何实现，暂不清楚）
         * constraints : 约束信息，Long类型，后2bit表示4种 长宽表示法。
         */
        val placeable = measurable.measure(constraints)
        Log.d("xxd2", "measurable=$measurable,measureScope=$this")
        Log.d("xxd2", "minWidth=${constraints.minWidth},maxWidth=${constraints.maxWidth},minHeight=${constraints.minHeight},maxHeight=${constraints.maxHeight}")

        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseLine = placeable[FirstBaseline]
        Log.d("xxd2", "firstBaseLine=$firstBaseLine")


        val placeableY = firstBaseLineToTop.roundToPx() - firstBaseLine
        val height = placeable.height + placeableY
        // 2. 设置具体的宽、高，这里就是确定了
        layout(placeable.width, height) {
            Log.d("xxd2", "width=${placeable.width},height=$height")
            // 3.布局子节点
            placeable.placeRelative(0, placeableY)
        }
    }
}

/**
 * 固有属性 intrinsic
 */
@Composable
fun TwoText(text1: String, text2: String) {
    Row(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)) {
        Text(
            text = text1, modifier = Modifier
                .weight(1f)
                .padding(start = 5.dp)
                .wrapContentWidth(align = Alignment.Start)
        )
        Divider(
            color = Color.Red,
            modifier = Modifier
                .width(2.dp)
                .fillMaxHeight()
        )
        Text(
            text = text2, modifier = Modifier
                .weight(1f)
                .padding(end = 5.dp)
                .wrapContentWidth(align = Alignment.End)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestCustomFirstBaseline() {
    MatrixAndroidTheme {
        Box {
            Text(
                text = "高高的青山，百花盛开！", modifier = Modifier
                    .customFirstBaselinePadding(10.dp)
            )
        }
    }
}

@Composable
fun CustomFirstBaseline() {
    Box {
        Text(
            text = "高高的青山，百花盛开！", modifier = Modifier
                .background(Color.Cyan)
                .customFirstBaselinePadding(30.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TwoTextTest() {
    Box() {
        TwoText("大人的", "没有压力")
    }
}