package com.xxd.myself.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.xxd.myself.R

/**
 * author : xxd
 * created time : 2021/6/11
 * describe : 带shape类似属性的TextView，懒得写重复的xml，
 *             background属性由本类生成，xml中的不起作用
 */
class ShapeTextView : AppCompatTextView {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        initAttribute(context, attrs)
    }

    @SuppressLint("CustomViewStyleable")
    fun initAttribute(context: Context?, attrs: AttributeSet?) {
        context?.let {
            val typeArray =
                it.obtainStyledAttributes(attrs, R.styleable.ShapeTextView)
            val radius =
                typeArray.getDimensionPixelSize(R.styleable.ShapeTextView_shapeRadiusTopLeft, 0)
            val strokeWidth =
                typeArray.getDimensionPixelSize(R.styleable.myself_custom_shape_myself_stroke_width, 0)
            val color =
                typeArray.getColor(R.styleable.myself_custom_shape__color, Color.TRANSPARENT)
            val strokeColor =
                typeArray.getColor(R.styleable.myself_custom_shape_myself_stroke_color, Color.TRANSPARENT)
            typeArray.recycle()

            val gradientDrawable = GradientDrawable()
            gradientDrawable.setColor(color)
            val radii = floatArrayOf(
                radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(),
                radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat()
            )
            gradientDrawable.cornerRadii = radii
            gradientDrawable.setStroke(strokeWidth, strokeColor)
            background = gradientDrawable
        }
    }
}