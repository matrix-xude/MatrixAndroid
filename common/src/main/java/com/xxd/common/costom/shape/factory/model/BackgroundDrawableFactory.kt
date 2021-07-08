package com.xxd.common.costom.shape.factory.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import com.xxd.common.R
import com.xxd.common.costom.shape.factory.SortDrawableFactory

/**
 * author : xxd
 * date   : 2021/6/14
 * desc   : 通过2张Drawable来实现按压变换效果
 */
class BackgroundDrawableFactory private constructor() : SortDrawableFactory() {

    private val selectedArray = intArrayOf(android.R.attr.state_pressed)
    private val normalArray = intArrayOf(-android.R.attr.state_pressed)

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            BackgroundDrawableFactory()
        }
    }


    override fun getUnSortList(): List<Int> {
        return listOf(
            R.attr.CustomShapeBackgroundNormal,
            R.attr.CustomShapeBackgroundPressed,
        )
    }

    override fun createDrawable(context: Context, attrs: AttributeSet?): Drawable {
        val ta = context.obtainStyledAttributes(attrs, sortedArray)
        val drawableNormal = ta.getDrawable(sortedMap[R.attr.CustomShapeBackgroundNormal]!!)
        val drawablePressed = ta.getDrawable(sortedMap[R.attr.CustomShapeBackgroundPressed]!!)
        ta.recycle()

        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(normalArray,drawableNormal)
        stateListDrawable.addState(selectedArray,drawablePressed)

        return stateListDrawable
    }
}