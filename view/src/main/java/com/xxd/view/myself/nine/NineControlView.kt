package com.xxd.view.myself.nine

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.FloatRange
import androidx.core.view.children
import com.xxd.view.R

/**
 *    author : xxd
 *    date   : 2021/8/30
 *    desc   : 九宫图控件，类似于RecyclerView的设计，这里只控制测量、布局操作（为了方便，包括圆角、间隔），
 *    具体每一个View的绘制填充，提供Adapter给外部自己适配
 */
class NineControlView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewGroup(context, attributeSet, defStyleAttr) {


    // 间距
    private var mInterval: Int
    // 圆角
    private var mRadius: Int

    /**
     * 当填充九宫图只有一个View的时候，宽度是按照控件的宽度的百分比来设置的
     */
    @FloatRange(from = 0.0, to = 1.0)
    var mOneViewWidthPercent: Float

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.NineControlView)
        mInterval = ta.getDimensionPixelSize(R.styleable.NineControlView_intervalWidth, DEFAULT_INTERVAL)
        mRadius = ta.getDimensionPixelSize(R.styleable.NineControlView_radius, DEFAULT_RADIUS)
        mOneViewWidthPercent = ta.getFloat(R.styleable.NineControlView_oneViewWidthPercent, DEFAULT_ONE_VIEW_WIDTH_PERCENT)
        ta.recycle()
    }

    // 适配器
    private lateinit var mAdapter: Adapter

    companion object {
        const val MAX_SHOW_VIEW = 9 // 最大展示图片数
        const val DEFAULT_INTERVAL = 15 // 默认间隔px
        const val DEFAULT_RADIUS = 0 // 默认圆角px
        const val DEFAULT_ONE_VIEW_WIDTH_PERCENT = 1f // 默认圆角px
    }

    /**
     * 外部使用
     */
    fun setAdapter(adapter: Adapter) {
        removeAllViews()
        mAdapter = adapter
        // 为了不默认创建多个View，默认创造9个ViewHolder
        val count = MAX_SHOW_VIEW.coerceAtMost(adapter.getCount())
        repeat(count) {
            val view = mAdapter.createView(it)
            if (view.layoutParams == null) { // 没有参数，都设为包裹
                addView(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            } else {
                addView(view)
            }
        }
    }

    /**
     * 获取Adapter
     */
    fun getAdapter(): Adapter? {
        if (!this::mAdapter.isInitialized)
            return null
        return mAdapter
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 如果adapter没设置，按照常规测量
        if (!this::mAdapter.isInitialized) {
            setMeasuredDimension(getCustomSize(suggestedMinimumWidth, widthMeasureSpec), getCustomSize(suggestedMinimumHeight, heightMeasureSpec))
            return
        }

        // 九宫图测量开始
        measureNine(widthMeasureSpec, heightMeasureSpec)
    }

    // 测量九宫图
    private fun measureNine(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 控件的测量宽度
        val defaultWidth = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        // 这种情况出现的可能：横滑的RecyclerView，并且没设置最小宽度、没有实际背景图
        if (defaultWidth == 0)
            return

        // 测量最后的宽高，需要在下面赋值
        var dimensionWidth = 0
        var dimensionHeight = 0
        if (mAdapter.getCount() == 0) {
            // 站位使用，防止走到else逻辑
        } else if (mAdapter.getCount() == 1) { // 只有一条数据的时候，宽度是动态设置的
            val view = getChildAt(0)  // 当前View是有数据的，这里是为了防止adapter修改和刷新不同步
            // 宽度是固定的
            val measureSpecWidth = MeasureSpec.makeMeasureSpec((defaultWidth * mOneViewWidthPercent).toInt(), MeasureSpec.EXACTLY)
            // 高度需要测量子类
            val measureSpecHeight = getChildMeasureSpec(heightMeasureSpec, 0, view.layoutParams.height)
            view.measure(measureSpecWidth, measureSpecHeight)
            // 自身宽高，就是子类宽高
            dimensionWidth = view.measuredWidth
            dimensionHeight = view.measuredHeight
        } else if (mAdapter.getCount() == 2 || mAdapter.getCount() == 4) { // 一行分2列，宽高固定
            // 宽高固定，且一样
            val childWidth = (defaultWidth - mInterval) / 2
            val measureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY)
            // 因为除不尽，导致尾部留1px没有填充问题，处理该问题
            children.forEachIndexed { index, view ->
                if (index % 2 == 1) { // 尾列
                    val endColumnWidth = defaultWidth - childWidth - mInterval // 尾列的宽度
                    view.measure(MeasureSpec.makeMeasureSpec(endColumnWidth, MeasureSpec.EXACTLY), measureSpec)
                } else {
                    view.measure(measureSpec, measureSpec)
                }
            }
            // 自身宽高
            val row = (mAdapter.getCount() + 1) / 2 // 最大有几行，从1开始
            val height = childWidth * row + mInterval * (row - 1).coerceAtLeast(0)
            dimensionWidth = defaultWidth
            dimensionHeight = height
        } else { // 一行分3列，3*3类型，宽高固定
            // 宽高固定，且一样
            val childWidth = (defaultWidth - 2 * mInterval) / 3
            val measureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY)
            // 因为除不尽，导致尾部留1px没有填充问题，处理该问题
            children.forEachIndexed { index, view ->
                if (index % 3 == 2) { // 尾列
                    val endColumnWidth = defaultWidth - childWidth * 2 - mInterval * 2 // 尾列的宽度
                    view.measure(MeasureSpec.makeMeasureSpec(endColumnWidth, MeasureSpec.EXACTLY), measureSpec)
                } else {
                    view.measure(measureSpec, measureSpec)
                }
            }
            // 自身宽高
            val row = (mAdapter.getCount().coerceAtMost(MAX_SHOW_VIEW) + 2) / 3 // 最大有几行
            val height = childWidth * row + mInterval * (row - 1).coerceAtLeast(0)
            dimensionWidth = defaultWidth
            dimensionHeight = height
        }
        // 不能小于最小值
        dimensionWidth = dimensionWidth.coerceAtLeast(getCustomSize(suggestedMinimumWidth, widthMeasureSpec))
        dimensionHeight = dimensionHeight.coerceAtLeast(getCustomSize(suggestedMinimumHeight, heightMeasureSpec))
        setMeasuredDimension(dimensionWidth, dimensionHeight)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // 如果adapter没设置，不需要布局
        if (!this::mAdapter.isInitialized)
            return

        if (mAdapter.getCount() == 1) { // 只有一条数据的时候
            getChildAt(0)?.let {
                it.layout(0, 0, it.measuredWidth, it.measuredHeight)
            }
        } else if (mAdapter.getCount() == 2 || mAdapter.getCount() == 4) { // 一行分2列，宽高固定
            children.forEachIndexed { index, view ->
                val row = index / 2 // 第几行
                val column = index % 2 // 第几列
                val left = column * view.measuredWidth + column * mInterval
                val top = row * view.measuredHeight + row * mInterval
                view.layout(left, top, left + view.measuredWidth, top + view.measuredHeight)
            }
        } else { // 一行分3列，3*3类型，宽高固定
            children.forEachIndexed { index, view ->
                val row = index / 3 // 第几行
                val column = index % 3 // 第几列
                val left = column * view.measuredWidth + column * mInterval
                val top = row * view.measuredHeight + row * mInterval
                view.layout(left, top, left + view.measuredWidth, top + view.measuredHeight)
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

    /**
     * 外部填充九宫图使用的Adapter
     */
    interface Adapter {
        /**
         * 返回需要展示九宫子控件的总数（超过9个会被当成9个处理）
         */
        fun getCount(): Int

        /**
         * 创建 View,用来填充九宫图控件
         * @param position 第n个图
         */
        fun createView(position: Int): View
    }

}