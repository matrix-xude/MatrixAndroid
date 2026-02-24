package com.xxd.service.ui.local

import androidx.compose.runtime.compositionLocalOf
import com.xxd.service.ui.theme.MyColor

/**
 *    author : xxd
 *    date   : 2024/4/5
 *    desc   :
 */

val LocalColor = compositionLocalOf<MyColor> { error("not provide color") }