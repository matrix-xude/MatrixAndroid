package com.xxd.view.myself.nine

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.FloatRange
import androidx.core.view.children
import com.xxd.common.util.log.LogUtil

/**
 *    author : xxd
 *    date   : 2021/8/30
 *    desc   : 九宫图控件，类似于RecyclerView的设计，这里只控制测量、布局操作（为了方便，包括圆角、间隔），
 *    具体每一个View的绘制填充，提供Adapter给外部自己适配
 */
class NineControlView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewGroup(context, attributeSet, defStyleAttr) {

    /**
     * 当填充九宫图只有一个View的时候，宽度是按照控件的宽度的百分比来设置的
     */
    @FloatRange(from = 0.0, to = 1.0)
    var mOneViewWidthPercent: Float = 0.8F

    // 间距
    var mInterval = DEFAULT_INTERVAL

    // 适配器
    private lateinit var mAdapter: Adapter

    // ViewHolder集合
    private val mViewHolderList = mutableListOf<ViewHolder>()

    // 当前绑定viewHolder的总数，只有调用refresh后才主动改变
    private var mTotalCount = 0

    companion object {
        const val MAX_SHOW_VIEW = 9 // 最大展示图片数
        const val DEFAULT_INTERVAL = 15 // 默认间隔
        const val ITEM_VIEW_TAG_KEY = 1 // 标记itemView的tag
    }

    /**
     * 外部使用
     */
    fun setAdapter(adapter: Adapter) {
        clearAll()
        mAdapter = adapter
        // 为了不默认创建多个View，默认创造9个ViewHolder
        val count = MAX_SHOW_VIEW
        repeat(count) {
            mViewHolderList.add(mAdapter.onCreateViewHolder())
        }
        // 添加子View
        mViewHolderList.forEach {
            if (it.itemView.layoutParams == null) { // 没有参数，都设为包裹
                addView(it.itemView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            } else {
                addView(it.itemView)
            }
        }
        // 绑定ViewHolder
        bindingViewHolder()
    }

    /**
     * 获取Adapter
     */
    fun getAdapter(): Adapter? {
        if (!this::mAdapter.isInitialized)
            return null
        return mAdapter
    }

    /**
     * 在已经设置了adapter下，刷新数据
     * 调用该方法会重新执行 [Adapter.onBindingViewHolder]，而不执行[Adapter.onCreateViewHolder]
     * 目的是为了解决在RecyclerView中使用该九宫图控件 频繁创建View导致的卡顿
     */
    fun refreshAdapter() {
        bindingViewHolder()
    }

    // 初始设置、刷新后需要重新绑定View
    private fun bindingViewHolder() {
        if (!this::mAdapter.isInitialized)
            return

        // 这里赋值，不时时从mAdapter获取数据
        mTotalCount = mAdapter.getCount().coerceAtMost(MAX_SHOW_VIEW)

//        mViewHolderList.forEachIndexed { index, viewHolder ->
//            viewHolder.itemView.visibility = if (index >= mTotalCount) View.GONE else View.VISIBLE
//        }
        repeat(mTotalCount) {
            mAdapter.onBindingViewHolder(mViewHolderList[it], it)
        }
        requestLayout()
    }

    // 清除所有的数据、view,一般在设置新adapter之前调佣
    private fun clearAll() {
        removeAllViews()
        mViewHolderList.clear()
        mTotalCount = 0
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
        LogUtil.d("九宫图measureNine")
        // 控件的测量宽度
        val defaultWidth = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        // 这种情况出现的可能：横滑的RecyclerView，并且没设置最小宽度、没有实际背景图
        if (defaultWidth == 0)
            return

        // 测量最后的宽高，需要在下面赋值
        var dimensionWidth = 0
        var dimensionHeight = 0
        if (mTotalCount == 0) {
            // 站位使用，防止走到else逻辑
        }
        else if (mTotalCount == 1) { // 只有一条数据的时候，宽度是动态设置的
            val view = getChildAt(0)  // 当前View是有数据的，这里是为了防止adapter修改和刷新不同步
            // 宽度是固定的
            val measureSpecWidth = MeasureSpec.makeMeasureSpec((defaultWidth * mOneViewWidthPercent).toInt(), MeasureSpec.EXACTLY)
            // 高度需要测量子类
            val measureSpecHeight = getChildMeasureSpec(heightMeasureSpec, 0, view.layoutParams.height)
            view.measure(measureSpecWidth, measureSpecHeight)
            // 自身宽高，就是子类宽高
            dimensionWidth = view.measuredWidth
            dimensionHeight = view.measuredHeight
        }
        else if (mTotalCount == 2 || mTotalCount == 4) { // 一行分2列，宽高固定
            // 宽高固定，且一样
            val childWidth = (defaultWidth - mInterval) / 2
            val measureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY)
            // 因为除不尽，导致尾部留1px没有填充问题，处理该问题
            children.forEachIndexed { index, view ->
                if (index >= mTotalCount)
                    return@forEachIndexed
                if (index % 2 == 1) { // 尾列
                    val endColumnWidth = defaultWidth - childWidth - mInterval // 尾列的宽度
                    view.measure(MeasureSpec.makeMeasureSpec(endColumnWidth, MeasureSpec.EXACTLY), measureSpec)
                } else {
                    view.measure(measureSpec, measureSpec)
                }
            }
            // 自身宽高
            val row = (mTotalCount + 1) / 2 // 最大有几行，从1开始
            val height = childWidth * row + mInterval * (row - 1).coerceAtLeast(0)
            dimensionWidth = defaultWidth
            dimensionHeight = height
        } else { // 一行分3列，3*3类型，宽高固定
            // 宽高固定，且一样
            val childWidth = (defaultWidth - 2 * mInterval) / 3
            val measureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY)
            // 因为除不尽，导致尾部留1px没有填充问题，处理该问题
            children.forEachIndexed { index, view ->
                if (index >= mTotalCount)
                    return@forEachIndexed
                if (index % 3 == 2) { // 尾列
                    val endColumnWidth = defaultWidth - childWidth * 2 - mInterval * 2 // 尾列的宽度
                    view.measure(MeasureSpec.makeMeasureSpec(endColumnWidth, MeasureSpec.EXACTLY), measureSpec)
                } else {
                    view.measure(measureSpec, measureSpec)
                }
            }
            // 自身宽高
            val row = (mTotalCount.coerceAtMost(MAX_SHOW_VIEW) + 2) / 3 // 最大有几行
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
        LogUtil.d("九宫图 onLayout ， changed=$changed")
        // 如果adapter没设置，不需要布局
        if (!this::mAdapter.isInitialized)
            return


        if (mTotalCount == 1) { // 只有一条数据的时候
            getChildAt(0)?.let {
                it.layout(0, 0, it.measuredWidth, it.measuredHeight)
            }
        } else if (mTotalCount == 2 || mTotalCount == 4) { // 一行分2列，宽高固定
            children.forEachIndexed { index, view ->
                if (index >= mTotalCount)
                    return@forEachIndexed
                val row = index / 2 // 第几行
                val column = index % 2 // 第几列
                val left = column * view.measuredWidth + column * mInterval
                val top = row * view.measuredHeight + row * mInterval
                view.layout(left, top, left + view.measuredWidth, top + view.measuredHeight)
            }
        } else { // 一行分3列，3*3类型，宽高固定
            children.forEachIndexed { index, view ->
                if (index >= mTotalCount)
                    return@forEachIndexed
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
         * 创建 ViewHolder,用来填充九宫图
         */
        fun onCreateViewHolder(): ViewHolder

        /**
         * 用来绑定viewHolder中的视图
         */
        fun onBindingViewHolder(viewHolder: ViewHolder, position: Int)
    }

    /**
     * 持有View的ViewHolder,方便外部扩展
     */
    abstract class ViewHolder(val itemView: View) {
        // 该viewHolder中的view是否展示
//        private var isShow = true
    }
}