package com.xxd.common.costom.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.annotation.IntDef
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 *    author : xxd
 *    date   : 2020/8/17
 *    desc   : 通用的ItemDecoration，适配了系统的3种layoutManager
 */
class CommonItemDecoration : RecyclerView.ItemDecoration() {

    companion object {
        // 当前RecyclerView的滑动方向
        private const val NONE = 0 // 没有确定方向，此状态下不绘制
        private const val VERTICAL = 1 // 垂直方向
        private const val HORIZONTAL = 2 // 水平方向

        /**
         * 限制ItemDecoration方向
         */
        @IntDef(NONE, VERTICAL, HORIZONTAL)
        @Retention(AnnotationRetention.SOURCE)
        @Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
        annotation class ItemDecorationOrientation

    }

    /**
     * 这些参数是可以用来外部设置的
     * 根据orientation的不同，注意设置参数的的需求
     */
    var boundaryTop = 0 // 顶部边距
    var boundaryBottom = 0 // 底部边距
    var boundaryLeft = 0 // 左边距
    var boundaryRight = 0 // 右边距
    var interval = 0 // 中间item的间距，不包含头尾
    var spanInterval = 0 // 当 spanCount>1 的时候，每个span之间的间距，处理时该间距前后各取1/2，所以最好设置偶数
    var boundary = 0  // 同时设置4个边距的数值
        set(value) {
            boundaryTop = value
            boundaryBottom = value
            boundaryLeft = value
            boundaryRight = value
            field = value
        }

    /**
     * 当前recyclerview方向
     */
    @ItemDecorationOrientation
    private var orientation = NONE

    /**
     * 当前recyclerview的layoutManager类型
     */
    private var layoutManager: RecyclerView.LayoutManager? = null

    /**
     * 初始化该recyclerview的滑动方向
     */
    private fun initOrientation(spanCount: Int, isVertical: Boolean) {
        this.spanCount = spanCount
        orientation = if (isVertical) VERTICAL else HORIZONTAL
    }

    /**
     * 滑动方向上每一行的item总数
     */
    private var spanCount = 1
        set(value) {
            if (value > 0) field = value
        }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        // 没获取到滑动方向前
        if (orientation == NONE) {
            when (val layoutManager = parent.layoutManager) {
                is GridLayoutManager ->
                    initOrientation(layoutManager.spanCount, layoutManager.orientation == LinearLayoutManager.VERTICAL)

                is LinearLayoutManager ->
                    initOrientation(1, layoutManager.orientation == LinearLayoutManager.VERTICAL)

                is StaggeredGridLayoutManager ->
                    initOrientation(layoutManager.spanCount, layoutManager.orientation == LinearLayoutManager.VERTICAL)

                else -> // 暂时不提供其它layoutManager的处理
                    Log.e(this::class.java.simpleName, "当前recyclerview无法处理${layoutManager?.javaClass?.simpleName}")
            }
        }
        layoutManager = parent.layoutManager

        // 获取不到滑动方向，不处理间隔
        if (orientation == NONE) {
            Log.e(this::class.java.simpleName, "当前recyclerview获取不到orientation")
            return
        }

        val adapter = parent.adapter
        // 如果adapter为null不处理间距
        adapter?.let {
            val position = parent.getChildAdapterPosition(view)

            // 找不到实际位置，不处理
            if (position == RecyclerView.NO_POSITION)
                return

            // 以下注释以竖直滑动为例，实际处理了竖直、水平2种滑动

            // 第一行：topOffset设置，非第一行：滑动方向上间隔设置
            val isFirstRow = position / spanCount == 0
            if (isFirstRow) {
                evaluateHead(outRect)
            } else { // 滑动方向上的间隔处理
                evaluateInterval(outRect)
            }

            // 最后一行：bottomOffset设置
            val isLastRow = position / spanCount == (it.itemCount - 1) / spanCount
            if (isLastRow) {
                evaluateTail(outRect)
            }

            // 第一列：leftOffset设置，非第一列：非滑动方向间隔设置
            var isFirstLine = position % spanCount == 0
            if (layoutManager is StaggeredGridLayoutManager) { // 瀑布流判断第一列需要特殊处理
                val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
                isFirstLine = layoutParams.spanIndex == 0
            }
            if (isFirstLine) {
                evaluateStart(outRect)
                if (spanCount > 1) {
                    evaluateSpanInterval(outRect, true)
                }
            } else { // 非滑动方向上的间隔处理
                evaluateSpanInterval(outRect, false)
                evaluateSpanInterval(outRect, true) // 最后一列尾部的多处理了，不过下面重新处理最后一列
            }

            // 最后一列：rightOffset设置
            var isLastLine = position % spanCount == spanCount - 1
            if (layoutManager is StaggeredGridLayoutManager) { // 瀑布流需要特殊处理
                val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
                isLastLine = layoutParams.spanIndex == spanCount - 1
            }
            if (isLastLine) {
                evaluateEnd(outRect)
            }
        }

    }

    /**
     * 给头部偏移量赋值
     */
    private fun evaluateHead(outRect: Rect) {
        if (orientation == VERTICAL) {
            outRect.top = boundaryTop
        }
        if (orientation == HORIZONTAL) {
            outRect.left = boundaryLeft
        }
    }

    /**
     * 给尾部偏移量赋值
     */
    private fun evaluateTail(outRect: Rect) {
        if (orientation == VERTICAL) {
            outRect.bottom = boundaryBottom
        }
        if (orientation == HORIZONTAL) {
            outRect.right = boundaryRight
        }
    }

    /**
     * BoundaryStart赋值
     */
    private fun evaluateStart(outRect: Rect) {
        if (orientation == VERTICAL) {
            outRect.left = boundaryLeft
        }
        if (orientation == HORIZONTAL) {
            outRect.top = boundaryTop
        }
    }

    /**
     * BoundaryEnd赋值
     */
    private fun evaluateEnd(outRect: Rect) {
        if (orientation == VERTICAL) {
            outRect.right = boundaryRight
        }
        if (orientation == HORIZONTAL) {
            outRect.bottom = boundaryBottom
        }
    }

    /**
     * 间隔赋值,默认赋值在上部、左部
     */
    private fun evaluateInterval(outRect: Rect) {
        if (orientation == VERTICAL) {
            outRect.top = interval
        }
        if (orientation == HORIZONTAL) {
            outRect.left = interval
        }
    }

    /**
     * 非滑动方向上的间隔赋值
     * @param isTail 是否为尾部，vertical下为bottom, horizontal下为right  true:是否尾部，false:不是尾部
     */
    private fun evaluateSpanInterval(outRect: Rect, isTail: Boolean) {
        if (orientation == VERTICAL) {
            if (isTail)
                outRect.right = spanInterval / 2
            else
                outRect.left = spanInterval / 2
        }
        if (orientation == HORIZONTAL) {
            if (isTail)
                outRect.bottom = spanInterval / 2
            else
                outRect.top = spanInterval / 2
        }
    }
}