package com.xxd.common.tool.shape.factory.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import com.orhanobut.logger.Logger
import com.xxd.common.R
import com.xxd.common.tool.shape.factory.FactoryHelper
import com.xxd.common.tool.shape.factory.SortDrawableFactory
import okhttp3.internal.toHexString

/**
 * author : xxd
 * date   : 2021/6/14
 * desc   : 按压后有效果变化的shape效果
 */
class ShapeSelectedDrawableFactory private constructor() : SortDrawableFactory() {

    private val selectedArray = intArrayOf(android.R.attr.state_pressed)
    private val normalArray = intArrayOf(-android.R.attr.state_pressed)

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ShapeSelectedDrawableFactory()
        }
    }

    override fun getUnSortList(): List<Int> {
        return listOf(
            // press之前的属性
            R.attr.CustomShapeRadius,
            R.attr.CustomShapeRadiusTopLeft,
            R.attr.CustomShapeRadiusTopRight,
            R.attr.CustomShapeRadiusBottomLeft,
            R.attr.CustomShapeRadiusBottomRight,
            R.attr.CustomShapeFillColor,
            R.attr.CustomShapeStrokeWidth,
            R.attr.CustomShapeStrokeColor,
            // press之后的属性
            R.attr.CustomShapeTransparencyScale,
            R.attr.CustomShapeRadiusPressed,
            R.attr.CustomShapeRadiusTopLeftPressed,
            R.attr.CustomShapeRadiusTopRightPressed,
            R.attr.CustomShapeRadiusBottomLeftPressed,
            R.attr.CustomShapeRadiusBottomRightPressed,
            R.attr.CustomShapeFillColorPressed,
            R.attr.CustomShapeStrokeWidthPressed,
            R.attr.CustomShapeStrokeColorPressed
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
        val fillColor =
            ta.getColor(sortedMap[R.attr.CustomShapeFillColor]!!, FactoryHelper.DEFAULT_COLOR)
        val strokeWidth = ta.getDimensionPixelOffset(
            sortedMap[R.attr.CustomShapeStrokeWidth]!!,
            FactoryHelper.DEFAULT_STROKE_WIDTH
        )
        val strokeColor = ta.getColor(
            sortedMap[R.attr.CustomShapeStrokeColor]!!,
            FactoryHelper.DEFAULT_STROKE_COLOR
        )

        val radiusPressed =
            ta.getDimension(
                sortedMap[R.attr.CustomShapeRadiusPressed]!!,
                FactoryHelper.DEFAULT_RADIUS
            )
        var radiusTopLeftPressed = ta.getDimension(
            sortedMap[R.attr.CustomShapeRadiusTopLeftPressed]!!,
            FactoryHelper.DEFAULT_RADIUS
        )
        var radiusTopRightPressed = ta.getDimension(
            sortedMap[R.attr.CustomShapeRadiusTopRightPressed]!!,
            FactoryHelper.DEFAULT_RADIUS
        )
        var radiusBottomLeftPressed = ta.getDimension(
            sortedMap[R.attr.CustomShapeRadiusBottomLeftPressed]!!,
            FactoryHelper.DEFAULT_RADIUS
        )
        var radiusBottomRightPressed = ta.getDimension(
            sortedMap[R.attr.CustomShapeRadiusBottomRightPressed]!!,
            FactoryHelper.DEFAULT_RADIUS
        )
        var fillColorPressed =
            ta.getColor(
                sortedMap[R.attr.CustomShapeFillColorPressed]!!,
                FactoryHelper.DEFAULT_COLOR
            )
        var transparencyScale = ta.getFloat(
            sortedMap[R.attr.CustomShapeTransparencyScale]!!,
            FactoryHelper.DEFAULT_TRANSPARENCY_SCALE
        )
        var strokeWidthPressed = ta.getDimensionPixelOffset(
            sortedMap[R.attr.CustomShapeStrokeWidthPressed]!!,
            FactoryHelper.DEFAULT_STROKE_WIDTH
        )
        var strokeColorPressed = ta.getColor(
            sortedMap[R.attr.CustomShapeStrokeColorPressed]!!,
            FactoryHelper.DEFAULT_STROKE_COLOR
        )
        ta.recycle()

        // Normal图片的创建
        val gradientDrawableNormal = GradientDrawable()

        radiusTopLeft = FactoryHelper.calculateRadius(radius, radiusTopLeft)
        radiusTopRight = FactoryHelper.calculateRadius(radius, radiusTopRight)
        radiusBottomLeft = FactoryHelper.calculateRadius(radius, radiusBottomLeft)
        radiusBottomRight = FactoryHelper.calculateRadius(radius, radiusBottomRight)
        gradientDrawableNormal.cornerRadii = floatArrayOf(
            radiusTopLeft, radiusTopLeft, radiusTopRight, radiusTopRight,
            radiusBottomRight, radiusBottomRight, radiusBottomLeft, radiusBottomLeft
        )

        gradientDrawableNormal.setColor(fillColor)
        gradientDrawableNormal.setStroke(strokeWidth, strokeColor)

        // pressed图片的创建,没有的数据复用上面的数据，省的同样的属性写很多遍
        val gradientDrawablePressed = GradientDrawable()

        radiusTopLeftPressed = FactoryHelper.calculateRadius(radiusPressed, radiusTopLeftPressed)
        radiusTopRightPressed = FactoryHelper.calculateRadius(radiusPressed, radiusTopRightPressed)
        radiusBottomLeftPressed =
            FactoryHelper.calculateRadius(radiusPressed, radiusBottomLeftPressed)
        radiusBottomRightPressed =
            FactoryHelper.calculateRadius(radiusPressed, radiusBottomRightPressed)

        radiusTopLeftPressed =
            copyNormal(radiusTopLeftPressed, FactoryHelper.DEFAULT_RADIUS, radiusTopLeft)
        radiusTopRightPressed =
            copyNormal(radiusTopRightPressed, FactoryHelper.DEFAULT_RADIUS, radiusTopRight)
        radiusBottomLeftPressed =
            copyNormal(radiusBottomLeftPressed, FactoryHelper.DEFAULT_RADIUS, radiusBottomLeft)
        radiusBottomRightPressed =
            copyNormal(radiusBottomRightPressed, FactoryHelper.DEFAULT_RADIUS, radiusBottomRight)
        gradientDrawablePressed.cornerRadii = floatArrayOf(
            radiusTopLeftPressed,
            radiusTopLeftPressed,
            radiusTopRightPressed,
            radiusTopRightPressed,
            radiusBottomRightPressed,
            radiusBottomRightPressed,
            radiusBottomLeftPressed,
            radiusBottomLeftPressed
        )

        // 当前pressed的fillColor没有赋值的时候，再起用transparencyScale属性
        if (fillColorPressed == FactoryHelper.DEFAULT_COLOR) {
            fillColorPressed = fillColor // 赋值为normal状态的值
            if (transparencyScale != FactoryHelper.DEFAULT_TRANSPARENCY_SCALE) {
                transparencyScale = if (transparencyScale > 1F) 1F else transparencyScale
                transparencyScale = if (transparencyScale < 0F) 0F else transparencyScale

                val alpha = fillColorPressed ushr 24
                val alpha2 = (alpha * transparencyScale).toInt()
                fillColorPressed = fillColorPressed shl 8 ushr 8 or (alpha2 shl 24)
            }
        }

        gradientDrawablePressed.setColor(fillColorPressed)

        strokeWidthPressed =
            copyNormal(strokeWidthPressed, FactoryHelper.DEFAULT_STROKE_WIDTH, strokeWidth)
        strokeColorPressed =
            copyNormal(strokeColorPressed, FactoryHelper.DEFAULT_STROKE_COLOR, strokeColor)
        gradientDrawablePressed.setStroke(strokeWidthPressed, strokeColorPressed)


        // 合成图片
        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(selectedArray, gradientDrawablePressed)
        stateListDrawable.addState(normalArray, gradientDrawableNormal)

        return stateListDrawable
    }

    /**
     * 根据当前值current==default,判断是否赋值normal的数据
     */
    private fun <T> copyNormal(current: T, default: T, normal: T): T {
        return if (current == default) normal else current
    }
}