package com.xxd.view.iconfont

import android.graphics.Typeface
import com.xxd.common.base.BaseApplication

/**
 *    author : xxd
 *    date   : 2022/4/8
 *    desc   :
 */
object FontType {

    /**
     * iconFont的字库
     */
    val iconFontTypeface: Typeface by lazy {
        Typeface.createFromAsset(BaseApplication.application.assets, "iconfont/iconfont.ttf")
    }

    /**
     * iconFont的字库
     */
    val iconFontTypeface2: Typeface by lazy {
        Typeface.createFromAsset(BaseApplication.application.assets, "iconfont2/iconfont.ttf")
    }

    /**
     * iconFont的字库
     */
    val iconFontTypeface3: Typeface by lazy {
        Typeface.createFromAsset(BaseApplication.application.assets, "iconfont2/iconfont.js")
    }

    /**
     * iconFont的字库
     */
    val iconFontTypeface4: Typeface by lazy {
        Typeface.createFromAsset(BaseApplication.application.assets, "iconfont3/iconfont.ttf")
    }

    /**
     * iconFont的字库
     */
    val iconFontTypeface5: Typeface by lazy {
        Typeface.createFromAsset(BaseApplication.application.assets, "iconfont3/iconfont.js")
    }
}