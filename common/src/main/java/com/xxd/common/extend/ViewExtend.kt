package com.xxd.common.extend

import android.view.View

/**
 * author : xxd
 * date   : 2021/7/11
 * desc   :
 */

/**
 * View扩展点击事件
 */
fun View.onClick(block: ((View) -> Unit)?) {
    this.setOnClickListener(block)
}