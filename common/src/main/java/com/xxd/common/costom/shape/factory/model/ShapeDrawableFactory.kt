package com.xxd.common.costom.shape.factory.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import com.xxd.common.R
import com.xxd.common.costom.shape.factory.FactoryHelper
import com.xxd.common.costom.shape.factory.SortDrawableFactory

/**
 * author : xxd
 * date   : 2021/6/13
 * desc   : 创造普通的shape属性解析图片
 */
class ShapeDrawableFactory private constructor() : SortDrawableFactory() {

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ShapeDrawableFactory()
        }
    }

    override fun getUnSortList(): List<Int> {
        return listOf(
            R.attr.CustomShapeRadius,
            R.attr.CustomShapeRadiusTopLeft,
            R.attr.CustomShapeRadiusTopRight,
            R.attr.CustomShapeRadiusBottomLeft,
            R.attr.CustomShapeRadiusBottomRight,
            R.attr.CustomShapeFillColor,
            R.attr.CustomShapeStrokeWidth,
            R.attr.CustomShapeStrokeColor
        )
    }

    override fun createDrawable(context: Context, attrs: AttributeSet?): Drawable {

        val ta = context.obtainStyledAttributes(attrs, sortedArray)
        val radius =
            ta.getDimension(sortedMap[R.attr.CustomShapeRadius]!!, FactoryHelper.DEFAULT_RADIUS)
        var radiusTopLeft = ta.getDimension(
            sortedMap[R.attr.CustomShapeRadiusTopLeft]!!,
            FactoryHelper.DEFAULT_RADIUS
        )
        var radiusTopRight = ta.getDimension(
            sortedMap[R.attr.CustomShapeRadiusTopRight]!!,
            FactoryHelper.DEFAULT_RADIUS
        )
        var radiusBottomLeft = ta.getDimension(
            sortedMap[R.attr.CustomShapeRadiusBottomLeft]!!,
            FactoryHelper.DEFAULT_RADIUS
        )
        var radiusBottomRight = ta.getDimension(
            sortedMap[R.attr.CustomShapeRadiusBottomRight]!!,
            FactoryHelper.DEFAULT_RADIUS
        )
        val fillColor = ta.getColor(sortedMap[R.attr.CustomShapeFillColor]!!, FactoryHelper.DEFAULT_COLOR)
        val strokeWidth = ta.getDimensionPixelOffset(
            sortedMap[R.attr.CustomShapeStrokeWidth]!!,
            FactoryHelper.DEFAULT_STROKE_WIDTH
        )
        val strokeColor = ta.getColor(
            sortedMap[R.attr.CustomShapeStrokeColor]!!,
            FactoryHelper.DEFAULT_STROKE_COLOR
        )
        ta.recycle()

        // 圆角半径约束
        radiusTopLeft = FactoryHelper.calculateRadius(radius, radiusTopLeft)
        radiusTopRight = FactoryHelper.calculateRadius(radius, radiusTopRight)
        radiusBottomLeft = FactoryHelper.calculateRadius(radius, radiusBottomLeft)
        radiusBottomRight = FactoryHelper.calculateRadius(radius, radiusBottomRight)

        // 图片的创建
        val gradientDrawable = GradientDrawable()
        gradientDrawable.cornerRadii = floatArrayOf(
            radiusTopLeft, radiusTopLeft, radiusTopRight, radiusTopRight,
            radiusBottomRight, radiusBottomRight, radiusBottomLeft, radiusBottomLeft
        )
        gradientDrawable.setColor(fillColor)
        gradientDrawable.setStroke(strokeWidth, strokeColor)

        return gradientDrawable
    }

}