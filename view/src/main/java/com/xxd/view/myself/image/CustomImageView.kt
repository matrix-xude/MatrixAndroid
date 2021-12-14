package com.xxd.view.myself.image

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.xxd.view.R

/**
 *    author : xxd
 *    date   : 2021/10/8
 *    desc   : 支持一些特殊功能的ImageView
 *    1. 四边可以单独设置圆角
 *    2. 外部可以绘制边框，边框的粗细、颜色可以单独设置
 */
class CustomImageView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatImageView(context, attributeSet, defStyleAttr) {


    // 圆角
    private var mRadius: Float
    private var mRadiusTopLeft: Float
    private var mRadiusTopRight: Float
    private var mRadiusBottomLeft: Float
    private var mRadiusBottomRight: Float

    // 边框
    private var mStrokeWidth: Float = DEFAULT_STROKE_WIDTH
    private var mStrokeColor: Int = DEFAULT_STROKE_COLOR

    // 画笔
    private var mRoundPaint: Paint
    private var mStrokePaint: Paint

    companion object {
        const val DEFAULT_RADIUS = 0f // 默认圆角px
        const val DEFAULT_STROKE_WIDTH = 0f // 默认边框px
        const val DEFAULT_STROKE_COLOR = Color.TRANSPARENT // 默认边框颜色px
    }

    fun applyRadius(radius: Float) {
        mRadius = radius
        mRadiusTopLeft = radius
        mRadiusTopRight = radius
        mRadiusBottomLeft = radius
        mRadiusBottomRight = radius
    }

    fun applyStroke(width: Float, color: Int) {
        mStrokeWidth = width
        mStrokeColor = color
        mStrokePaint.strokeWidth = mStrokeWidth
        mStrokePaint.color = mStrokeColor
    }

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.CustomImageView)
        mRadius = ta.getDimension(R.styleable.CustomImageView_civ_radius, DEFAULT_RADIUS)
        mRadiusTopLeft = ta.getDimension(R.styleable.CustomImageView_civ_radius_top_left, DEFAULT_RADIUS)
        mRadiusTopRight = ta.getDimension(R.styleable.CustomImageView_civ_radius_top_right, DEFAULT_RADIUS)
        mRadiusBottomLeft = ta.getDimension(R.styleable.CustomImageView_civ_radius_bottom_left, DEFAULT_RADIUS)
        mRadiusBottomRight = ta.getDimension(R.styleable.CustomImageView_civ_radius_bottom_right, DEFAULT_RADIUS)
        mStrokeWidth = ta.getDimension(R.styleable.CustomImageView_civ_stroke_width, DEFAULT_STROKE_WIDTH)
        mStrokeColor = ta.getColor(R.styleable.CustomImageView_civ_stroke_color, DEFAULT_STROKE_COLOR)
        ta.recycle()

        // 没有单独设置圆角,全部配置成全局圆角
        if (mRadiusTopLeft == DEFAULT_RADIUS && mRadiusTopRight == DEFAULT_RADIUS && mRadiusBottomLeft == DEFAULT_RADIUS && mRadiusBottomRight == DEFAULT_RADIUS) {
            mRadiusTopLeft = mRadius
            mRadiusTopRight = mRadius
            mRadiusBottomLeft = mRadius
            mRadiusBottomRight = mRadius
        }

        mRoundPaint = Paint().apply {
            color = Color.WHITE
            isAntiAlias = true
            style = Paint.Style.FILL
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        }
        mStrokePaint = Paint().apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeWidth = mStrokeWidth
            color = mStrokeColor
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

        canvas.saveLayer(RectF(0f, 0f, width.toFloat(), height.toFloat()), null)
        super.onDraw(canvas)

        // 边框的处理
        if (mStrokeWidth > 0) {
            val path = Path()
            path.addRoundRect(
                RectF(0f, 0f, width.toFloat(), height.toFloat()),
                floatArrayOf(mRadiusTopLeft, mRadiusTopLeft, mRadiusTopRight, mRadiusTopRight, mRadiusBottomRight, mRadiusBottomRight, mRadiusBottomLeft, mRadiusBottomLeft),
                Path.Direction.CCW
            )
            canvas.drawPath(path, mStrokePaint)
        }

        // 圆角的处理
//        drawTopLeft(canvas, PointF(0f, 0f), mRadiusTopLeft)
//        drawTopRight(canvas, PointF(width.toFloat(), 0f), mRadiusTopRight)
//        drawBottomLeft(canvas, PointF(0f, height.toFloat()), mRadiusBottomLeft)
//        drawBottomRight(canvas, PointF(width.toFloat(), height.toFloat()), mRadiusBottomRight)

        drawTopLeft(canvas, PointF(0f - 1, 0f - 1), mRadiusTopLeft + 1)
        drawTopRight(canvas, PointF(width.toFloat() + 1, 0f - 1), mRadiusTopRight + 1)
        drawBottomLeft(canvas, PointF(0f - 1, height.toFloat() + 1), mRadiusBottomLeft + 1)
        drawBottomRight(canvas, PointF(width.toFloat() + 1, height.toFloat() + 1), mRadiusBottomRight + 1)

        canvas.restore()
    }

    // 左上画圆角
    private fun drawTopLeft(canvas: Canvas, pointF: PointF, radius: Float) {
        if (radius <= 0) return
        val path = Path()
        path.moveTo(pointF.x, pointF.y + radius)
        path.lineTo(pointF.x, pointF.y)
        path.lineTo(pointF.x + radius, pointF.y)
        path.arcTo(RectF(pointF.x, pointF.y, pointF.x + radius * 2, pointF.y + radius * 2), -90f, -90f)
        path.close()
        canvas.drawPath(path, mRoundPaint)

//        path.moveTo(0f, radius)
//        path.arcTo(RectF(pointF.x, pointF.y, pointF.x + radius * 2, pointF.y + radius * 2), 180f, 90f)
//        path.lineTo(width.toFloat(), 0f)
//        path.lineTo(width.toFloat(), height.toFloat())
//        path.lineTo(0f, height.toFloat())
//        path.close()

//        path.moveTo(width.toFloat(),0f)
//        path.lineTo(width.toFloat(),height.toFloat())
//        path.lineTo(0f,height.toFloat())
//        path.lineTo(width.toFloat(),0f)
//        canvas.drawPath(path, mRoundPaint)
    }

    // 右上画圆角
    private fun drawTopRight(canvas: Canvas, pointF: PointF, radius: Float) {
        if (radius <= 0) return
        val path = Path()
        path.moveTo(pointF.x, pointF.y + radius)
        path.lineTo(pointF.x, pointF.y)
        path.lineTo(pointF.x - radius, pointF.y)
        path.arcTo(RectF(pointF.x - radius * 2, pointF.y, pointF.x, pointF.y + radius * 2), -90f, 90f)
        path.close()
        canvas.drawPath(path, mRoundPaint)

//        path.moveTo(width.toFloat(), radius)
//        path.arcTo(RectF(pointF.x - radius * 2, pointF.y, pointF.x, pointF.y + radius * 2), 0f, -90f)
//        path.lineTo(0f, 0f)
//        path.lineTo(0f, height.toFloat())
//        path.lineTo(width.toFloat(), height.toFloat())
//        path.close()
//        canvas.drawPath(path, mRoundPaint)
    }

    // 左下画圆角
    private fun drawBottomLeft(canvas: Canvas, pointF: PointF, radius: Float) {
        if (radius <= 0) return
        val path = Path()
        path.moveTo(pointF.x, pointF.y - radius)
        path.lineTo(pointF.x, pointF.y)
        path.lineTo(pointF.x + radius, pointF.y)
        path.arcTo(RectF(pointF.x, pointF.y - radius * 2, pointF.x + radius * 2, pointF.y), 90f, 90f)
        path.close()
        canvas.drawPath(path, mRoundPaint)

//        path.moveTo(0f, width.toFloat() - radius)
//        path.arcTo(RectF(pointF.x, pointF.y - radius * 2, pointF.x + radius * 2, pointF.y), 180f, -90f)
//        path.lineTo(width.toFloat(), height.toFloat())
//        path.lineTo(width.toFloat(), 0f)
//        path.lineTo(0f, 0f)
//        path.close()
//        canvas.drawPath(path, mRoundPaint)
    }

    // 右下画圆角
    private fun drawBottomRight(canvas: Canvas, pointF: PointF, radius: Float) {
        if (radius <= 0) return
        val path = Path()
        path.moveTo(pointF.x, pointF.y - radius)
        path.lineTo(pointF.x, pointF.y)
        path.lineTo(pointF.x - radius, pointF.y)
        path.arcTo(RectF(pointF.x - radius * 2, pointF.y - radius * 2, pointF.x, pointF.y), 90f, -90f)
        path.close()
        canvas.drawPath(path, mRoundPaint)

//        path.moveTo(width.toFloat(), height - radius)
//        path.arcTo(RectF(pointF.x - radius * 2, pointF.y - radius * 2, pointF.x, pointF.y), 0f, 90f)
//        path.lineTo(0f, height.toFloat())
//        path.lineTo(0f, 0f)
//        path.lineTo(width.toFloat(), 0f)
//        path.close()
//        canvas.drawPath(path, mRoundPaint)
    }
}