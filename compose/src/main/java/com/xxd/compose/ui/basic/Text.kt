package com.xxd.compose.ui.basic

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 *    author : xxd
 *    date   : 2024/1/28
 *    desc   : compse基础组件测试
 */

@Preview(showBackground = true, name = "SimpleText_1")
@Composable
fun SimpleText(){
    Text(text = "Hello , Revolution")
}