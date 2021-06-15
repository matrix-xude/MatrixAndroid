package com.xxd.common.tool.shape.factory.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.xxd.common.R
import com.xxd.common.tool.shape.factory.DrawableFactory
import com.xxd.common.tool.shape.factory.SortDrawableFactory

/**
 * author : xxd
 * date   : 2021/6/14
 * desc   : 判断使用哪种解析的图片工厂
 */
class JudgeDrawableFactory private constructor() : SortDrawableFactory() {

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            JudgeDrawableFactory()
        }
    }

    override fun getUnSortList(): List<Int> {
        return listOf(
            R.attr.CustomShapeCanPress,
            R.attr.CustomShapeCanBackground
        )
    }

    override fun createDrawable(context: Context, attrs: AttributeSet?): Drawable {
        val ta = context.obtainStyledAttributes(attrs, sortedArray)
        val shapeUseSelected = ta.getBoolean(sortedMap[R.attr.CustomShapeCanPress]!!, false)
        val backgroundUseSelected =
            ta.getBoolean(sortedMap[R.attr.CustomShapeCanBackground]!!, false)
        ta.recycle()

        val factory: DrawableFactory = if (backgroundUseSelected) {
            BackgroundDrawableFactory.instance
        } else {
            if (shapeUseSelected) ShapeSelectedDrawableFactory.instance else ShapeDrawableFactory.instance
        }

        return factory.createDrawable(context, attrs)
    }
}