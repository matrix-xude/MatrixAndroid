package com.xxd.view.recycler

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.annotation.IntDef
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 *    author : xxd
 *    date   : 2020/8/17
 *    desc   :
 */
class CommonItemDecoration() : RecyclerView.ItemDecoration() {

    companion object {

        const val NONE = 0 // 没有确定方向，此状态下不绘制
        const val VERTICAL = 1 // 垂直方向，默认
        const val HORIZONTAL = 2 // 水平方向

        /**
         * 限制ItemDecoration方向
         */
        @IntDef(NONE, VERTICAL, HORIZONTAL)
        @Retention(AnnotationRetention.SOURCE)
        annotation class ItemDecorationOrientation {}
    }

    /**
     * 根据 orientation 来区分以下参数的含义
     * ex: head,tail 表示recyclerview滑动的开始、结束两端，VERTICAL下即为上下方向，HORIZONTAL即为左右方向
     */
    var headOffset = 0 // 滑动开始端方向偏移量
    var tailOffset = 0 // 滑动结束端方向偏移量
    var boundary = 0  // 同时设置 boundaryStart,boundaryEnd 2个数值
        set(value) {
            boundaryStart = value
            boundaryEnd = value
            field = value
        }
    var boundaryStart = 0 // 整体的"左"（VERTICAL下为"上"）边距
    var boundaryEnd = 0 // 整体的"右"（VERTICAL下为"下"）边距
    var interval = 0 // 中间item的间距，不包含头尾

    @ItemDecorationOrientation
    var orientation: Int = NONE // 当前ItemDecoration方向

    /**
     * 滑动方向上每一行的总数
     */
    private var orientationCount = 1
        set(value) {
            if (value > 0) field = value
        }


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    private fun initOrientation(spanCount: Int, isVertical: Boolean) {
        orientationCount = spanCount
        orientation = if (isVertical) VERTICAL else HORIZONTAL
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        if (orientation == NONE) {
            when (val layoutManager = parent.layoutManager) {
                is GridLayoutManager -> initOrientation(
                    layoutManager.spanCount,
                    layoutManager.orientation == LinearLayoutManager.VERTICAL
                )
                is LinearLayoutManager -> initOrientation(
                    1,
                    layoutManager.orientation == LinearLayoutManager.VERTICAL
                )
                is StaggeredGridLayoutManager -> initOrientation(
                    layoutManager.spanCount,
                    layoutManager.orientation == LinearLayoutManager.VERTICAL
                )
            }
        }

        val adapter = parent.adapter
        // 如果adapter为null不处理间距
        adapter?.let {
            val position = parent.getChildAdapterPosition(view)
            // 以前注释以竖直滑动为例，实际处理了2竖直、水平2种滑动
            if (position / orientationCount == 0) { // 第一行
                evaluateHead(outRect, headOffset)
            } else { // 滑动方向上的间隔处理
                evaluateInterval(outRect, interval, true)
            }

            if (position / orientationCount == (it.itemCount - 1) / orientationCount) { // 最后一行
                evaluateTail(outRect, tailOffset)
            }

            if (position % orientationCount == 0) { // 第一列
                evaluateBoundaryStart(outRect, boundaryStart)
            } else { // 非滑动方向上的间隔处理
                evaluateInterval(outRect, interval, false)
            }

            if (position % orientationCount == orientationCount - 1) { // 最后一列
                evaluateBoundaryEnd(outRect, boundaryEnd)
            }
        }

    }

    /**
     * 给头部偏移量赋值
     */
    private fun evaluateHead(outRect: Rect, offset: Int) {
        if (orientation == VERTICAL) {
            outRect.top = offset
        } else {
            outRect.left = offset
        }
    }

    /**
     * 给尾部偏移量赋值
     */
    private fun evaluateTail(outRect: Rect, offset: Int) {
        if (orientation == VERTICAL) {
            outRect.bottom = offset
        } else {
            outRect.right = offset
        }
    }

    /**
     * BoundaryStart赋值
     */
    private fun evaluateBoundaryStart(outRect: Rect, offset: Int) {
        if (orientation == VERTICAL) {
            outRect.left = offset
        } else {
            outRect.top = offset
        }
    }

    /**
     * BoundaryEnd赋值
     */
    private fun evaluateBoundaryEnd(outRect: Rect, offset: Int) {
        if (orientation == VERTICAL) {
            outRect.right = offset
        } else {
            outRect.bottom = offset
        }
    }

    /**
     * 间隔赋值,默认赋值在上部、左部
     * @param isScrollOrientationLine 是否为滑动方向上的行，true:是 false:否
     */
    private fun evaluateInterval(outRect: Rect, offset: Int, isScrollOrientationLine: Boolean) {
        if (orientation == VERTICAL) {
            if (isScrollOrientationLine)
                outRect.top = offset
            else
                outRect.left = offset
        } else {
            if (isScrollOrientationLine)
                outRect.left = offset
            else
                outRect.top = offset
        }
    }
}