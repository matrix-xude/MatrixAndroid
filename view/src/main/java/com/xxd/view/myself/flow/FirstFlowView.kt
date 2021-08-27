package com.xxd.view.myself.flow

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import com.xxd.common.util.log.LogUtil
import com.xxd.view.myself.util.MeasureUtil

/**
 *    author : xxd
 *    date   : 2021/8/26
 *    desc   : 自定义ViewGroup的尝试
 *    自定义一个流式布局，类似标签页，根据内部的子View宽度自动换行
 *    1. 处理了padding
 *    2. 没有处理子类的margin
 *    3. 如果放于滑动控件中，只能处于数值滑动的控件，横滑无法处理自动换行
 */
class FirstFlowView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewGroup(context, attributeSet, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)  // 父类测量了background

        MeasureUtil.logMeasureSpec(widthMeasureSpec, "width")
        MeasureUtil.logMeasureSpec(heightMeasureSpec, "height")
        initParam()

        measureMyChildView(widthMeasureSpec, heightMeasureSpec)
    }

    // 初始化每次测量需要用到的参数
    private fun initParam() {
        mMaxMeasureWidth = 0
        mMaxMeasureHeight = 0
        mLineInfoList.clear()
    }

    private var mMaxMeasureWidth = 0 // 父类给出的最大可以使用宽度
    private var mMaxMeasureHeight = 0 // 父类给出的最大可以使用高度
    private var mLineInfoList = mutableListOf<LineInfo>() // 保存测量后的所有信息
    private var mIntervalWidth = 20 // 横行之间的间隔，不包括左右，左右使用padding
    private var mIntervalHeight = 10 // 竖行之间的间隔，不包括上下，上下使用padding


    // 测量子View,重新布局
    private fun measureMyChildView(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 根据父类确定的最大约束
        // MeasureSpec.UNSPECIFIED下如果没有minWidth或者真实background,获取到的是0，无法完成自动换行效果
        mMaxMeasureWidth = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        mMaxMeasureHeight = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)

        var currentLineNumber = 1 // 当前的行
        children.forEach { view ->
            measureChild(view, widthMeasureSpec, heightMeasureSpec)
            val childInfo = ChildMeasureInfo(view.measuredWidth, view.measuredHeight)
            // 第一行添加
            mLineInfoList.takeIf { it.isEmpty() }?.add(LineInfo(mutableListOf(), currentLineNumber, 0))
            // 当前行已经存储的View的Measure信息
            val currentLine = mLineInfoList.last()
            if (currentLine.list.isEmpty()) { // 只有第一行可能出现，直接添加进去
                currentLine.list.add(childInfo)
                currentLine.maxHeight = childInfo.measuredHeight
            } else {
                var usedWidth = paddingLeft + paddingRight // 已经使用了的width
                currentLine.list.forEach {
                    usedWidth += it.measuredWidth + mIntervalWidth
                }

                if (usedWidth + childInfo.measuredWidth > mMaxMeasureWidth) { // 再次添加进来的View超过最大measureWidth,需要折行
                    currentLineNumber++
                    mLineInfoList.add(LineInfo(mutableListOf(childInfo), currentLineNumber, childInfo.measuredHeight))
                } else {  // 当前最后一行可以添加此View进去
                    currentLine.maxHeight = currentLine.maxHeight.coerceAtLeast(childInfo.measuredHeight) // 更新最大行高
                    currentLine.list.add(childInfo)
                }
            }
        }

        var width1 = mMaxMeasureWidth
        var height1 = mMaxMeasureHeight

        // 结束子View测量循环之后，再计算自己的宽高
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        if (mode == MeasureSpec.AT_MOST) {
            var width = 0
            mLineInfoList.forEach { info ->
                val widthInterval = 0.coerceAtMost((info.list.size - 1) * mIntervalWidth) // 间隔的距离
                var widthViews = 0
                info.list.forEach { widthViews += it.measuredWidth }
                val totalWidth = widthInterval + widthViews
                width = width.coerceAtLeast(totalWidth)
            }
            width += paddingLeft + paddingRight
            width1 = width1.coerceAtMost(width)
        }
        val mode2 = MeasureSpec.getMode(heightMeasureSpec)
        if (mode2 == MeasureSpec.AT_MOST || mode2 == MeasureSpec.UNSPECIFIED) {
            var height = 0
            // 数值方向需要计算间隔
            height += 0.coerceAtLeast((mLineInfoList.size - 1) * mIntervalHeight) // 间隔的距离
            mLineInfoList.forEach { info ->
                height += info.maxHeight
            }
            height += paddingTop + paddingBottom
            if (mode2 == MeasureSpec.AT_MOST)
                height1 = height1.coerceAtMost(height)
            if (mode2 == MeasureSpec.UNSPECIFIED)  // 高度不限制下，需要多少就是多少
                height1 = height.coerceAtLeast(height1)
        }

        setMeasuredDimension(width1, height1)
    }

    // l t r b 是相对于父布局的 左 上 右 下，不是相对于屏幕的
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        LogUtil.d("onLayout 布局开始 changed=$changed,left=$l,top=$t,right=$r,bottom=$b,")

        var index = 0
        var tempTop = paddingTop
        do {
            mLineInfoList.removeFirstOrNull()?.run {
                var tempLeft = paddingLeft
                list.forEach {
                    val child = getChildAt(index)
                    child.layout(tempLeft, tempTop, tempLeft + it.measuredWidth, tempTop + it.measuredHeight)
                    index++
                    tempLeft += it.measuredWidth + mIntervalWidth
                }
                tempTop += this.maxHeight + mIntervalHeight
            }
        } while (mLineInfoList.isNotEmpty())

    }

    data class LineInfo(
        val list: MutableList<ChildMeasureInfo>,
        val lineNum: Int,
        var maxHeight: Int
    )

    data class ChildMeasureInfo(
        val measuredWidth: Int,
        val measuredHeight: Int,
    )
}