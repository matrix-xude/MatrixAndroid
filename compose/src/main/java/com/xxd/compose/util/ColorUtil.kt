package com.xxd.compose.util

import androidx.compose.ui.graphics.Color

object ColorUtil {

    /**
     * 获取随机compose颜色
     */
    fun randomColor(): Color {
        return Color(red = (0..255).random(), green = (0..255).random(), blue = (0..255).random())
    }
}