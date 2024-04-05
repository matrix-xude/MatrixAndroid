package com.xxd.service.ui.theme

import androidx.compose.ui.graphics.Color

// dark mode color
val dark_theme_primary = Color(0xFFD0BCFF)
val dark_theme_secondary = Color(0xFFCCC2DC)
val dark_theme_tertiary = Color(0xFFEFB8C8)
val dark_theme_background = Color.Black
val dark_theme_title = Color.White
val dark_theme_background_second = Color(0xff000080)

// light mode color
val light_theme_primary = Color(0xFF6650a4)
val light_theme_secondary = Color(0xFF625b71)
val light_theme_tertiary = Color(0xFF7D5260)
val light_theme_background = Color.White
val light_theme_title = Color.Black
val light_theme_background_second = Color(0xffafeeee)

class MyColor(private val isLight: Boolean = true) {
    val primary = if (isLight) light_theme_primary else dark_theme_primary
    val secondary = if (isLight) light_theme_secondary else dark_theme_secondary
    val tertiary = if (isLight) light_theme_tertiary else dark_theme_tertiary
    val background = if (isLight) light_theme_background else dark_theme_background
    val title = if (isLight) light_theme_title else dark_theme_title
    val backgroundSecond = if (isLight) light_theme_background_second else dark_theme_background_second
}