package com.xxd.compose.ui.lazy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.xxd.compose.R
import com.xxd.compose.data.FakeData
import com.xxd.compose.data.FirstItem

/**
 *    author : xxd
 *    date   : 2024/3/5
 *    desc   :
 */

@Composable
fun FirstList(list: List<FirstItem>) {
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(contentPadding = PaddingValues(vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            // 第一条
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.settings), contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .size(25.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        text = "顶部条目", fontSize = TextUnit(dimensionResource(id = R.dimen.sp_17).value, TextUnitType.Sp),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 20.dp)
                    )
                }
            }
            // 所有条目
            items(count = list.size) {
                FirstListItem(item = list[it])
            }
        }
    }
}

@Composable
fun FirstListItem(item: FirstItem) {

    ConstraintLayout(modifier = Modifier.fillMaxWidth().background(Color(0x22ff0000)).padding(0.dp).clickable {  }) {
        val (icon, text1, text2) = createRefs()

        Icon(painter = painterResource(id = R.drawable.facebook), contentDescription = "", tint = Color.Cyan,
            modifier = Modifier
                .size(20.dp)
                .constrainAs(icon) {
                    start.linkTo(parent.start, 0.dp)
                    top.linkTo(parent.top, 10.dp)
                })
        Text(text = item.name, color = Color.Magenta, fontSize = 16.sp, modifier = Modifier.constrainAs(text1) {
            start.linkTo(icon.end, 20.dp)
            bottom.linkTo(icon.bottom)
        })

        Text(text = item.desc, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.constrainAs(text2) {
            start.linkTo(icon.start)
            top.linkTo(icon.bottom, 5.dp)
            bottom.linkTo(parent.bottom, 10.dp)
        })
    }
}

@Preview(name = "test1", showBackground = true)
@Composable
fun TestFirstList() {
    val fakeFirstList = FakeData.fakeFirstList()
    FirstList(fakeFirstList)
}

@Preview(name = "test2", showBackground = true)
@Composable
fun TestFirstListItem() {
    val firstList = FirstItem("高大上", "出山志在登熬顶")
    FirstListItem(firstList)
}