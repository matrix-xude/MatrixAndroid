package com.xxd.common.costom.shape.factory

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet

/**
 * author : xxd
 * date   : 2021/6/13
 * desc   : 创造图片
 */
interface DrawableFactory {

    /**
     * 生成一张drawable
     */
    fun createDrawable(context: Context, attrs: AttributeSet?): Drawable
}