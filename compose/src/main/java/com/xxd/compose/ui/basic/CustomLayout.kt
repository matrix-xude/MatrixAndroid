package com.xxd.compose.ui.basic

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp

/**
 *    author : xxd
 *    date   : 2024/3/4
 *    desc   : 自定义Layout
 */

@Composable
fun MyCustomLayout(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables: List<Measurable>, constraints: Constraints ->
        val placeables = measurables.map {
            it.measure(constraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var yPosition = 0

            placeables.forEach {
                it.place(0, yPosition)
                yPosition += it.height
            }
        }
    }
}

// 增加对固有属性的处理
@Composable
fun MyCustomLayout2(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content, measurePolicy = object : MeasurePolicy {

        override fun MeasureScope.measure(measurables: List<Measurable>, constraints: Constraints): MeasureResult {
            val placeables = measurables.map {
                it.measure(constraints)
            }

            return layout(constraints.maxWidth, constraints.maxHeight) {
                var yPosition = 0

                placeables.forEach {
                    it.place(0, yPosition)
                    yPosition += it.height
                }
            }
        }

        override fun IntrinsicMeasureScope.minIntrinsicWidth(measurables: List<IntrinsicMeasurable>, height: Int): Int {
            return 150
        }

        override fun IntrinsicMeasureScope.maxIntrinsicWidth(measurables: List<IntrinsicMeasurable>, height: Int): Int {
            return 500
        }

        override fun IntrinsicMeasureScope.minIntrinsicHeight(measurables: List<IntrinsicMeasurable>, width: Int): Int {
            return 250
        }

        override fun IntrinsicMeasureScope.maxIntrinsicHeight(measurables: List<IntrinsicMeasurable>, width: Int): Int {
            return 100
        }
    })
}

@Preview(showBackground = true)
@Composable
fun TestMyCustomLayout() {
    Surface {
        MyCustomLayout2(modifier = Modifier.padding(10.dp).width(IntrinsicSize.Max)) {
            Text(modifier = Modifier.fillMaxWidth(), text = "高级")
//            Icon(modifier = Modifier.size(30.dp), tint = Color.Red, painter = painterResource(id = R.drawable.settings), contentDescription = "")
            Text(modifier = Modifier.fillMaxWidth(),text = "dsfdaf dfdsfadsf")
            Text(modifier = Modifier.fillMaxWidth(),text = "超级 YMC")
        }
    }
}