package com.xxd.view.myself.onelinehead

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.children
import com.xxd.view.R
import com.xxd.view.myself.image.CustomImageView

/**
 *    author : xxd
 *    date   : 2021/10/11
 *    desc   : 一行头像，后一个覆盖前一个一部分（如20%），最多显示10个头像
 */
class OneLineHeadView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewGroup(context, attributeSet, defStyleAttr) {

    private var mMaxNumber: Int // 最大可展示头像数
    private var mHeadRadius: Int // 头像半径
    private var mStrokeWidth: Float // 头像边框
    private var mStrokeColor: Int // 边框颜色
    private var mCoverDimension: Float // 后一个覆盖前一个的距离

    // 适配器
    private var mAdapter: Adapter? = null

    /**
     * 设置适配器
     */
    fun setAdapter(adapter: Adapter) {
        mAdapter = adapter
        requestLayout()
    }

    companion object {
        const val DEFAULT_MAX_NUMBER = 10 // 默认最大显示头像个数
        const val DEFAULT_HEAD_RADIUS = 30 // 默认头像半径
        const val DEFAULT_STROKE_WIDTH = 0f // 默认头像边框px
        const val DEFAULT_STROKE_COLOR = Color.TRANSPARENT // 默认边框颜色px
        const val DEFAULT_COVER_DIMENSION = 10f // 默认后一个覆盖前一个的距离
    }

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.OneLineHeadView)
        mMaxNumber = ta.getInt(R.styleable.OneLineHeadView_olh_max_number, DEFAULT_MAX_NUMBER)
        mHeadRadius = ta.getDimensionPixelOffset(R.styleable.OneLineHeadView_olh_head_radius, DEFAULT_HEAD_RADIUS)
        mStrokeWidth = ta.getDimension(R.styleable.OneLineHeadView_olh_stroke_width, DEFAULT_STROKE_WIDTH)
        mStrokeColor = ta.getColor(R.styleable.OneLineHeadView_olh_stroke_color, DEFAULT_STROKE_COLOR)
        mCoverDimension = ta.getDimension(R.styleable.OneLineHeadView_olh_cover_dimension, DEFAULT_COVER_DIMENSION)
        ta.recycle()

        // 开始添加最大view,节约性能，不需要每次重复创建
        repeat(mMaxNumber) {
            addView(createHeadView())
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (mAdapter == null || mAdapter!!.getCount() == 0) {
            setMeasuredDimension(getCustomSize(suggestedMinimumWidth, widthMeasureSpec), getCustomSize(suggestedMinimumWidth, heightMeasureSpec))
            return
        }

        measureHead(widthMeasureSpec, heightMeasureSpec)
    }

    // 测量头像
    private fun measureHead(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val headNumber = mAdapter!!.getCount().coerceAtMost(mMaxNumber)
        for (index in 0 until mMaxNumber) {
            // 需要展示的头像才显示,必须不为GONE的测量才有意义
            getChildAt(index).visibility = if (index + headNumber >= mMaxNumber) View.VISIBLE else View.GONE
        }
        var allWidth = 0 // 所有Visible的View总宽度，相加，再减去叠加的部分
        var allHeight = 0 // 所有View的总高度，取最大值
        children.forEach {
            if (it.visibility == View.VISIBLE) {
                measureChild(it, widthMeasureSpec, heightMeasureSpec)
                allWidth += it.measuredWidth
                allHeight = allHeight.coerceAtLeast(it.measuredHeight)
            }
        }
        // 减去覆盖的部分
        allWidth -= ((headNumber - 1).coerceAtLeast(0) * mCoverDimension).toInt()

        // 确定自身的宽高
        val myselfWidth = getCustomSize(allWidth + paddingStart + paddingEnd, widthMeasureSpec)
        val myselfHeight = getCustomSize(allHeight + paddingTop + paddingBottom, heightMeasureSpec)
        setMeasuredDimension(myselfWidth, myselfHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (mAdapter == null || mAdapter!!.getCount() == 0) {
            return
        }

        val headNumber = mAdapter!!.getCount().coerceAtMost(mMaxNumber)
        // 距离右边的边距，需要不断调整
        var marginRight = paddingEnd
        for (index in mMaxNumber - 1 downTo 0) {
            val imageView = getChildAt(index) as CustomImageView
            if (imageView.visibility == View.VISIBLE) {
                imageView.layout(measuredWidth - marginRight - imageView.measuredWidth, paddingTop, measuredWidth - marginRight, paddingTop + imageView.measuredHeight)
                marginRight += imageView.measuredWidth - mCoverDimension.toInt()
                mAdapter?.fillHead(headNumber - (mMaxNumber - index), imageView)
            } else {
                break
            }

        }
    }

    // 获取通常的测量的值，与ViewGroup默认测量不同的地方：MeasureSpec.AT_MOST的时候，使用的不是最大值，而是最小值
    private fun getCustomSize(size: Int, measureSpec: Int): Int {
        var result = size
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        when (specMode) {
            MeasureSpec.UNSPECIFIED, MeasureSpec.AT_MOST -> result = size
            MeasureSpec.EXACTLY -> result = specSize
        }
        return result
    }

    // 创建一个头像View
    private fun createHeadView(): View {
        return CustomImageView(context).apply {

            layoutParams = LayoutParams(mHeadRadius * 2, mHeadRadius * 2)
            scaleType = ImageView.ScaleType.CENTER_CROP
            applyRadius(mHeadRadius.toFloat())
            applyStroke(mStrokeWidth, mStrokeColor)
        }
    }

    interface Adapter {

        /**
         * 获取头像的总数,如果超过[mMaxNumber],则只会取最后的[mMaxNumber]个数适配头像
         */
        fun getCount(): Int

        /**
         * 填充头像，提供给外部处理如何填充头像，控件只负责创建、测量ImageView
         * @param position 从左往右显示的第几个头像
         * @param imageView 当前的ImageView
         */
        fun fillHead(position: Int, imageView: ImageView)

    }
}