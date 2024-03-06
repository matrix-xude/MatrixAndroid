package com.xxd.compose.ui.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import com.xxd.compose.ui.theme.MatrixAndroidTheme

/**
 *    author : xxd
 *    date   : 2024/3/4
 *    desc   : ConstraintLayout使用
 */

@Composable
fun UseConstraintLayout() {

    ConstraintLayout {
        // 这里返回值为 ConstrainedLayoutReference
        val (button, text2, text,t1,t2,t3) = createRefs()

        // 引导线
        val guideline1 = createGuidelineFromStart(0.2f)
        val guideline2 = createGuidelineFromTop(10.dp)

        // 屏障线
        val barrier = createBottomBarrier(button, text)

        // chains
        createHorizontalChain(t1,t2,t3, chainStyle =  ChainStyle.SpreadInside)

        Button(onClick = { }, modifier = Modifier
            // constrainAs就可以绑定到上面定义的 ConstrainedLayoutReference
            .constrainAs(button) {
                start.linkTo(guideline1, margin = 0.dp)
                top.linkTo(guideline2)
            }) {
            Text(text = "确定")
        }

        Text(
            text = "练得身形似鹤形", color = Color(0xffff11ff), modifier = Modifier
                .constrainAs(text) {
                    start.linkTo(button.end, margin = 10.dp)
                    top.linkTo(button.bottom)
                }
                .clip(RoundedCornerShape(5.dp))
                .background(Color.Gray)
        )

        Text(
            text = "高达啊高达", color = Color(0xffffffff), modifier = Modifier
                .constrainAs(text2) {
                    top.linkTo(barrier)
                    start.linkTo(button.start)
                }
                .clip(RoundedCornerShape(5.dp))
                .background(Color.Cyan)
        )

        Text(
            text = "文字1", color = Color.Red, modifier = Modifier
                .constrainAs(t1) {
                    start.linkTo(parent.start)
                    top.linkTo(text2.bottom, 20.dp)
                }
                .clip(RoundedCornerShape(5.dp))
                .background(Color.Cyan)
        )


        Text(
            text = "文字2", color = Color.Blue, modifier = Modifier
                .constrainAs(t2) {

                }
                .clip(RoundedCornerShape(5.dp))
                .background(Color.Cyan)
        )

        Text(
            text = "文字3", color = Color.Red, modifier = Modifier
                .constrainAs(t3) {

                }
                .clip(RoundedCornerShape(5.dp))
                .background(Color.Cyan)
        )
    }
}

@Composable
fun UseConstraintLayout2() {

    BoxWithConstraints {
        val constraintSet = if (maxWidth > 300.dp)
            convertConstraintSet(20.dp)
        else
            convertConstraintSet(10.dp)

        ConstraintLayout(constraintSet = constraintSet) {
            OutlinedButton(onClick = { }, modifier = Modifier.layoutId("button")) {
                Text(text = "确定2")
            }
            Text(
                text = "千株松下两函经", modifier = Modifier
                    .layoutId("text")
                    .border(0.5.dp, Color.Red, RoundedCornerShape(4.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Cyan)
                    .padding(10.dp)
            )
        }
    }
}

private fun convertConstraintSet(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            start.linkTo(parent.start, margin = margin)
            top.linkTo(parent.top, margin = margin / 2)
        }
        constrain(text) {
            start.linkTo(button.end, margin = margin)
            top.linkTo(button.bottom)
        }
    }
}

@Preview
@Composable
fun TestUseConstraintLayout() {
    Surface(modifier = Modifier.fillMaxSize()) {
        UseConstraintLayout()
    }
}

@Preview
@Composable
fun TestUseConstraintLayout2() {
    Surface(modifier = Modifier.width(290.dp)) {
        UseConstraintLayout2()
    }
}
