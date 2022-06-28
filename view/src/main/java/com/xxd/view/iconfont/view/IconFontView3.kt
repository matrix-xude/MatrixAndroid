package com.xxd.view.iconfont.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.xxd.view.iconfont.FontType

/**
 *    author : xxd
 *    date   : 2022/4/8
 *    desc   : 测试阿里的iconFont
 *    1. 单色iconFont可以非常好的使用，设置1种颜色（textColor），其它部位透明
 *    2. 多色iconFont其实是svg图的转换(没成功)
 */
class IconFontView3 @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatTextView(context, attrs, defStyleAttr) {

        init {
            // 设置为iconfont的类型
            typeface = FontType.iconFontTypeface3
        }

}