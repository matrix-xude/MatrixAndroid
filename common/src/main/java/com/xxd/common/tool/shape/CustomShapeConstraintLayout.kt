package com.xxd.common.tool.shape

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * author : xxd
 * date   : 2021/6/13
 * desc   :
 */
class CustomShapeConstraintLayout : ConstraintLayout {

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