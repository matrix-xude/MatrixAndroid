package com.xxd.common.costom.shape

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


/**
 * author : xxd
 * date   : 2021/6/13
 * desc   : 带全部自定义属性的TextView
 */
class CustomShapeTextView : AppCompatTextView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        background =
            ShapeController.instance.provideDrawableFactory().createDrawable(context, attrs)
    }
}