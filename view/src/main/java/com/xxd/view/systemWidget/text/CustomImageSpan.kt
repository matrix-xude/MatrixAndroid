package com.xxd.view.systemWidget.text

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.style.ImageSpan
import androidx.annotation.IntDef
import java.lang.ref.WeakReference

/**
 *    author : xxd
 *    date   : 2021/9/18
 *    desc   : ImageSpan的对齐模式有3种
 *    1:ALIGN_BOTTOM  2:ALIGN_BASELINE  3:ALIGN_CENTER
 *    但是只有在API 29 才能使用模式的设置，无法兼容到下面的API
 *
 *    此类是把API 29的各种draw 模式拿出来，自己绘制一遍，这样就可以兼容29以下的图片绘制
 */
class CustomImageSpan : ImageSpan {

    private var imageType: @ImageSpanType Int = TYPE_CENTER

    constructor(context: Context, bitmap: Bitmap, type: @ImageSpanType Int = TYPE_CENTER) : super(context, bitmap) {
        imageType = type
    }

    constructor(drawable: Drawable, type: @ImageSpanType Int = TYPE_CENTER) : super(drawable) {
        imageType = type
    }

    constructor(drawable: Drawable, source: String, type: @ImageSpanType Int = TYPE_CENTER) : super(drawable, source) {
        imageType = type
    }

    constructor(context: Context, uri: Uri, type: @ImageSpanType Int = TYPE_CENTER) : super(context, uri) {
        imageType = type
    }

    constructor(context: Context, resourceId: Int, type: @ImageSpanType Int = TYPE_CENTER) : super(context, resourceId) {
        imageType = type
    }


    companion object {
        const val TYPE_CENTER = 1
        const val TYPE_BOTTOM = 2
        const val TYPE_BASELINE = 3
    }

    // 需要图片展示的类型，模仿API 29
    @Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
    @IntDef(TYPE_CENTER, TYPE_BOTTOM, TYPE_BASELINE)
    @Retention(AnnotationRetention.SOURCE)
    annotation class ImageSpanType


    private var mDrawableRef: WeakReference<Drawable?>? = null

    // 完全模仿API 29的绘制方式
    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {

        val b: Drawable = getCachedDrawable()?:return // 无图不绘制
        canvas.save()

        var transY = bottom - b.bounds.bottom
        if (imageType == TYPE_BASELINE) {
            transY -= paint.fontMetricsInt.descent
        } else if (imageType == TYPE_CENTER) {
            transY = (bottom - top) / 2 - b.bounds.height() / 2
        }

        canvas.translate(x, transY.toFloat())
        b.draw(canvas)
        canvas.restore()
    }

    private fun getCachedDrawable(): Drawable? {
        val wr: WeakReference<Drawable?>? = mDrawableRef
        var d: Drawable? = null
        if (wr != null) {
            d = wr.get()
        }
        if (d == null) {
            d = drawable
            mDrawableRef = WeakReference<Drawable?>(d)
        }
        return d
    }

}