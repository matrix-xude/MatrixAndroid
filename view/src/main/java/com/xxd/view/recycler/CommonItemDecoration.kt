package com.xxd.view.recycler

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.annotation.IntDef
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.orhanobut.logger.Logger

/**
 *    author : xxd
 *    date   : 2020/8/17
 *    desc   : 通用的ItemDecoration，适配了系统的3种layoutManager
 */
class CommonItemDecoration : RecyclerView.ItemDecoration() {

    companion object {

        const val NONE = 0 // 没有确定方向，此状态下不绘制
        const val VERTICAL = 1 // 垂直方向
        const val HORIZONTAL = 2 // 水平方向

        /**
         * 限制ItemDecoration方向
         * kotlin 1.0.3之后就不支持IntDef等，此处限制无效，改用enum
         */
        @IntDef(NONE, VERTICAL, HORIZONTAL)
        @Retention(AnnotationRetention.SOURCE)
        @Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
        annotation class ItemDecorationOrientation {}

        /**
         * 当前recyclerview的滑动方向
         */
        enum class Orientation {
            NONE, VERTICAL, HORIZONTAL
        }

        /**
         * 当前recyclerview的layoutManager类型
         */
        enum class LayoutManager {
            // 对应3种系统的manager,自定义manager暂时未处理
            NONE, Grid, Linear, StaggeredGrid
        }
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
    var boundaryStart = 0 // 整体的"左"（HORIZONTAL下为"上"）边距
    var boundaryEnd = 0 // 整体的"右"（HORIZONTAL下为"下"）边距
    var interval = 0 // 中间item的间距，不包含头尾
    var spanInterval = 0 // 当 spanCount>1 的时候，每个span之间的间距，处理时该间距前后各取1/2，所以最好设置偶数

    /**
     * 当前recyclerview方向
     */
    private var orientation = Orientation.NONE

    /**
     * 当前recyclerview的layoutManager类型
     */
    private var layoutManager = LayoutManager.NONE

    /**
     * 初始化该recyclerview的滑动方向
     */
    private fun initOrientation(spanCount: Int, isVertical: Boolean) {
        this.spanCount = spanCount
        orientation = if (isVertical) Orientation.VERTICAL else Orientation.HORIZONTAL
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

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        // 没获取到滑动方向前
        if (orientation == Orientation.NONE) {
            when (val layoutManager = parent.layoutManager) {
                is GridLayoutManager -> {
                    initOrientation(
                        layoutManager.spanCount,
                        layoutManager.orientation == LinearLayoutManager.VERTICAL
                    )
                    this.layoutManager = LayoutManager.Grid
                }
                is LinearLayoutManager -> {
                    initOrientation(
                        1,
                        layoutManager.orientation == LinearLayoutManager.VERTICAL
                    )
                    this.layoutManager = LayoutManager.Linear
                }
                is StaggeredGridLayoutManager -> {
                    initOrientation(
                        layoutManager.spanCount,
                        layoutManager.orientation == LinearLayoutManager.VERTICAL
                    )
                    this.layoutManager = LayoutManager.StaggeredGrid
                }
                else -> {
                    Logger.e("当前recyclerview获取不到layoutManager")
                    this.layoutManager = LayoutManager.NONE
                }
            }
        }

        // 获取不到滑动方向，不处理间隔
        if (orientation == Orientation.NONE) {
            Logger.e("当前recyclerview获取不到orientation")
            return
        }

        val adapter = parent.adapter
        // 如果adapter为null不处理间距
        adapter?.let {
            val position = parent.getChildAdapterPosition(view)

            // 以下注释以竖直滑动为例，实际处理了2竖直、水平2种滑动

            // 第一行：headOffset设置，非第一行：滑动方向上间隔设置
            val isFirstRow = position / spanCount == 0
            if (isFirstRow) {
                evaluateHead(outRect, headOffset)
            } else { // 滑动方向上的间隔处理
                evaluateInterval(outRect, interval)
            }

            // 最后一行：tailOffset设置
            val isLastRow = position / spanCount == (it.itemCount - 1) / spanCount
            if (isLastRow) {
                evaluateTail(outRect, tailOffset)
            }

            // 第一列：boundaryStart设置，非第一列：非滑动方向间隔设置
            var isFirstLine = position % spanCount == 0
            if (layoutManager == LayoutManager.StaggeredGrid) { // 瀑布流判断第一列需要特殊处理
                val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
                isFirstLine = layoutParams.spanIndex == 0
            }
            if (isFirstLine) {
                evaluateBoundaryStart(outRect, boundaryStart)
                if (spanCount > 1) {
                    evaluateSpanInterval(outRect, spanInterval / 2, true)
                }
            } else { // 非滑动方向上的间隔处理
                evaluateSpanInterval(outRect, spanInterval / 2, false)
                evaluateSpanInterval(outRect, spanInterval / 2, true) // 最后一列尾部的多处理了，不过下面重新处理最后一列
            }

            // 最后一列：boundaryEnd设置
            var isLastLine = position % spanCount == spanCount - 1
            if (layoutManager == LayoutManager.StaggeredGrid) { // 瀑布流需要特殊处理
                val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
                isLastLine = layoutParams.spanIndex == spanCount - 1
            }
            if (isLastLine) {
                evaluateBoundaryEnd(outRect, boundaryEnd)
                if (spanCount > 1) {
                    evaluateSpanInterval(outRect, spanInterval / 2, false)
                }
            }

        }

    }

    /**
     * 给头部偏移量赋值
     */
    private fun evaluateHead(outRect: Rect, offset: Int) {
        if (orientation == Orientation.VERTICAL) {
            outRect.top = offset
        }
        if (orientation == Orientation.HORIZONTAL) {
            outRect.left = offset
        }
    }

    /**
     * 给尾部偏移量赋值
     */
    private fun evaluateTail(outRect: Rect, offset: Int) {
        if (orientation == Orientation.VERTICAL) {
            outRect.bottom = offset
        }
        if (orientation == Orientation.HORIZONTAL) {
            outRect.right = offset
        }
    }

    /**
     * BoundaryStart赋值
     */
    private fun evaluateBoundaryStart(outRect: Rect, offset: Int) {
        if (orientation == Orientation.VERTICAL) {
            outRect.left = offset
        }
        if (orientation == Orientation.HORIZONTAL) {
            outRect.top = offset
        }
    }

    /**
     * BoundaryEnd赋值
     */
    private fun evaluateBoundaryEnd(outRect: Rect, offset: Int) {
        if (orientation == Orientation.VERTICAL) {
            outRect.right = offset
        }
        if (orientation == Orientation.HORIZONTAL) {
            outRect.bottom = offset
        }
    }

    /**
     * 间隔赋值,默认赋值在上部、左部
     */
    private fun evaluateInterval(outRect: Rect, offset: Int) {
        if (orientation == Orientation.VERTICAL) {
            outRect.top = offset
        }
        if (orientation == Orientation.HORIZONTAL) {
            outRect.left = offset
        }
    }

    /**
     * 非滑动方向上的间隔赋值
     * @param isTail 是否为尾部，vertical下为bottom, horizontal下为right  true:是否尾部，false:不是尾部
     */
    private fun evaluateSpanInterval(outRect: Rect, offset: Int, isTail: Boolean) {
        if (orientation == Orientation.VERTICAL) {
            if (isTail)
                outRect.right = offset
            else
                outRect.left = offset
        }
        if (orientation == Orientation.HORIZONTAL) {
            if (isTail)
                outRect.bottom = offset
            else
                outRect.top = offset
        }
    }
}