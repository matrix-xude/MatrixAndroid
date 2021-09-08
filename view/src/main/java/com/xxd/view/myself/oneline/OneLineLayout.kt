package com.xxd.view.myself.oneline

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.xxd.view.R

/**
 *    author : xxd
 *    date   : 2021/9/8
 *    desc   : 只包裹一行view的ViewGroup，处理某些部分需要优先测量（如某一行文字，后部分的需要显示完整，前部分的可以用…类显示）
 *    列子：[https://app.mockplus.cn/app/ojf6C61wa3wr/specs/design/gb36PcDis9_m8]
 */
class OneLineLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewGroup(context, attributeSet, defStyleAttr) {

    // 每个子View之间的间距
    private var mInterval: Int

    // 子View的对齐方式
    private var mAlignAt: Int

    // 已经被测量的子View的index集合，只有包含该index的才会被layout
    private val mMeasuredIndexList = mutableListOf<Int>()

    // 已经测量的子view中最大的高度
    private var mMaxMeasureHeight = 0

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.OneLineLayout)
        mInterval = ta.getDimensionPixelSize(R.styleable.OneLineLayout_oneLineInterval, DEFAULT_INTERVAL)
        mAlignAt = ta.getInt(R.styleable.OneLineLayout_oneLineAlignAt, ALIGN_AT_CENTER)
        ta.recycle()
    }

    companion object {
        const val DEFAULT_INTERVAL = 15 // 默认间隔px
        const val ALIGN_AT_CENTER = 0 // 子view居中对齐
        const val ALIGN_AT_TOP = 1 // 子view居上对齐
        const val ALIGN_AT_BOTTOM = 2 // 子view居下对齐
    }

    // 清除之前的测量数据
    private fun clearMeasuredData() {
        mMeasuredIndexList.clear()
        mMaxMeasureHeight = 0
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        clearMeasuredData() // 清空之前的数据

        val defaultWidth = getDefaultSize(0, widthMeasureSpec)
        if (defaultWidth <= 0) { // 这种情况一般是在横滑控件中使用，并且宽度不是固定值
            setMeasuredDimension(0, 0)
            return
        }

        // 测量开始
        measureMyself(widthMeasureSpec, heightMeasureSpec)
    }


    // 按照优先级测量
    private fun measureMyself(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 必然>0
        val defaultWidth = getDefaultSize(0, widthMeasureSpec)

        // 将子View按照优先级的字段进行排序
        val list = mutableListOf<Pair<Int, View>>()
        children.forEachIndexed { index, view ->
            list.add(Pair(index, view))
        }
        list.sortBy {
            val layoutParams = it.second.layoutParams as LayoutParams
            - layoutParams.priority
        }

        var usedWidth = 0 // 已经使用过的宽度
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        // 开始测量子View
        run loop@{  // 为了中断forEach循环
            list.forEach {
                // 结束循环的标志
                if (usedWidth + paddingStart + paddingEnd >= defaultWidth)
                    return@loop

                if (it.second.visibility == GONE) // 跳过Gone
                    return@forEach
                // 计算出剩余可以使用的宽度
                val remainMeasureSpec = MeasureSpec.makeMeasureSpec(defaultWidth - usedWidth, widthMode)
                measureChild(it.second, remainMeasureSpec, heightMeasureSpec)
                // 标志该子view可以被layout
                mMeasuredIndexList.add(it.first)
                // 已经使用的宽度为：该子view的测量宽度+间隔
                usedWidth += it.second.measuredWidth + mInterval
                // 最大高度更新
                mMaxMeasureHeight = mMaxMeasureHeight.coerceAtLeast(it.second.measuredHeight)
            }
        }

        // 测量自己
        var width = defaultWidth
        var height = mMaxMeasureHeight + paddingTop + paddingEnd
        // 宽度为包裹特殊
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            var tempWidth = (list.size - 1).coerceAtLeast(0) * mInterval
            tempWidth += (paddingStart + paddingEnd)
            list.forEach {
                tempWidth += it.second.measuredWidth
            }
            width = tempWidth
        }
        // 高度固定特殊
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY)
            height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)

    }

    // 已经是计算了padding的
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        var tempLeft = 0
        children.forEachIndexed { index, view ->
            if (mMeasuredIndexList.contains(index)) {  // 只布局测量过的子View
                var tempTop = 0
                when (mAlignAt) {
                    ALIGN_AT_CENTER -> tempTop += (mMaxMeasureHeight - view.measuredHeight) / 2
                    ALIGN_AT_TOP -> tempTop += 0
                    ALIGN_AT_BOTTOM -> tempTop += mMaxMeasureHeight - view.measuredHeight
                }
                view.layout(l + tempLeft, t + tempTop, l + tempLeft + view.measuredWidth, t + tempTop + view.measuredHeight)
                tempLeft += view.measuredWidth + mInterval  // 距左边距增加该view的宽度+1个间距
            }
        }
    }

    // 检测LayoutParams的类型是否正确，必须覆写
    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is LayoutParams
    }

    // 默认addView生成参数的方法，必须覆写，否则类型转换错误
    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    // 通过xml解析到子View的属性，必须覆写，不然拿不到子View的优先级属性
    override fun generateLayoutParams(attrs: AttributeSet?): ViewGroup.LayoutParams {
        return LayoutParams(context, attrs)
    }

    // addViewInner调用，必须覆写
    override fun generateLayoutParams(p: ViewGroup.LayoutParams?): ViewGroup.LayoutParams {
        return when (p) {
            is LayoutParams -> LayoutParams(p)
            is MarginLayoutParams -> LayoutParams(p)
            else -> LayoutParams(p)
        }
    }

    class LayoutParams : MarginLayoutParams {

        companion object {
            const val DEFAULT_PRIORITY = 0 // 默认测量优先级
        }

        var priority = DEFAULT_PRIORITY

        @SuppressLint("CustomViewStyleable")
        constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {
            val a = c.obtainStyledAttributes(attrs, R.styleable.OneLineLayout)
            priority = a.getInt(R.styleable.OneLineLayout_oneLinePriority, DEFAULT_PRIORITY)
            a.recycle()
        }

        constructor(width: Int, height: Int) : super(width, height)

        constructor(width: Int, height: Int, priority: Int) : super(width, height) {
            this.priority = priority
        }

        constructor(p: ViewGroup.LayoutParams?) : super(p)

        constructor(source: MarginLayoutParams?) : super(source)

        constructor(source: LayoutParams) : super(source) {
            this.priority = source.priority
        }
    }

}