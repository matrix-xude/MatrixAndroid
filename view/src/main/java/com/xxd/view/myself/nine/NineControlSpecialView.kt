package com.xxd.view.myself.nine

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.FloatRange
import androidx.core.view.children
import com.xxd.common.util.log.LogUtil
import com.xxd.view.R

/**
 *    author : xxd
 *    date   : 2021/9/22
 *    desc   : 特殊的九宫图控件
 *    (规则)[https://app.mockplus.cn/app/ojf6C61wa3wr/specs/design/5qrH2kOiPswQ]
 *    1. 1图模式中的宽、高适配由控件来实现（外部需要在LayoutParameter中传入固定的宽、高）
 *    2. 各种图下的圆角由本类控制，不能外部设置
 */
class NineControlSpecialView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewGroup(context, attributeSet, defStyleAttr) {

    // 间距
    private var mInterval: Int

    // 圆角，1图4圆角；5图5圆角；拐角处都是圆角
    private var mRadius: Float

    // 圆角截取画笔
    private var mRoundPaint: Paint = Paint()

    // 保证背景色不变的画笔
    private var mImagePaint: Paint = Paint()

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.NineControlSpecialView)
        mInterval = ta.getDimensionPixelSize(R.styleable.NineControlSpecialView_nineControlIntervalWidth, DEFAULT_INTERVAL)
        mRadius = ta.getDimension(R.styleable.NineControlSpecialView_nineControlRadius, DEFAULT_RADIUS)
        ta.recycle()

        mRoundPaint.color = Color.WHITE
        mRoundPaint.isAntiAlias = true
        mRoundPaint.style = Paint.Style.FILL
        mRoundPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)

        mImagePaint.xfermode = null
    }

    // 适配器
    private var mAdapter: Adapter? = null

    companion object {
        const val MAX_SHOW_VIEW = 9 // 最大展示图片数
        const val DEFAULT_INTERVAL = 15 // 默认间隔px
        const val DEFAULT_RADIUS = 0F // 默认圆角px
    }

    /**
     * 外部使用
     */
    fun setAdapter(adapter: Adapter) {
        removeAllViews()
        mAdapter = adapter
        val count = MAX_SHOW_VIEW.coerceAtMost(adapter.getCount())
        repeat(count) {
            val view = mAdapter!!.createView(it)
            addView(view)
        }
    }

    /**
     * 获取Adapter
     */
    fun getAdapter(): Adapter? {
        return mAdapter
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 如果adapter没设置，按照常规测量
        if (mAdapter == null) {
            setMeasuredDimension(getCustomSize(suggestedMinimumWidth, widthMeasureSpec), getCustomSize(suggestedMinimumHeight, heightMeasureSpec))
            return
        }

        // 九宫图测量开始
        measureNine(widthMeasureSpec, heightMeasureSpec)
    }

    // 测量九宫图
    private fun measureNine(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mAdapter ?: return
        // 控件的测量宽度
        val defaultWidth = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        // 这种情况出现的可能：横滑的RecyclerView，并且没设置最小宽度、没有实际背景图
        if (defaultWidth == 0)
            return

        // 测量最后的宽高，需要在下面赋值
        var dimensionWidth = 0
        var dimensionHeight = 0
        when (mAdapter!!.getCount()) {
            0 -> {  // 站位使用，防止走到else逻辑
                dimensionWidth = defaultWidth
                dimensionHeight = 0
            }
            1 -> { // 只有一条数据的时候，宽度是动态设置的
                val view = getChildAt(0)

                // 1图中的子View如果宽、高设置的是固定值，则按照九宫图的1图模式适配；如果宽、高其中有有一个<=0,则按照宽取该控件最大值，高自适应取适配
                val childLayoutParams = view.layoutParams
                if (childLayoutParams.width <= 0 || childLayoutParams.height <= 0) { // 不符合适配模式的九宫图
//                    LogUtil.d("当前九宫的1图模式，不是固定宽高的 width=${childLayoutParams.width} height=${childLayoutParams.height}")
                    // 宽度是固定的
                    val measureSpecWidth = MeasureSpec.makeMeasureSpec(defaultWidth, MeasureSpec.EXACTLY)
                    // 高度需要测量子类
                    val measureSpecHeight = getChildMeasureSpec(heightMeasureSpec, 0, view.layoutParams.height)
                    view.measure(measureSpecWidth, measureSpecHeight)
                } else {
//                    LogUtil.d("当前九宫的1图模式，固定宽高 width=${childLayoutParams.width} height=${childLayoutParams.height}")
                    val oneWidth = (defaultWidth - 2 * mInterval) / 3 // 9图中1图的宽度
                    val baseWidth = oneWidth * 2 + mInterval  // 判断标准为2图 + 1间隔
                    if (childLayoutParams.width >= baseWidth) {  // wiki中 1-1，1-2，1-3的适配方式
                        when {
                            childLayoutParams.width > childLayoutParams.height -> { // 宽 > 高，1-1的适配方式
                                // 宽度是固定的
                                val measureSpecWidth = MeasureSpec.makeMeasureSpec(baseWidth, MeasureSpec.EXACTLY)
                                // 高度也是固定的，数值为宽度的3/4
                                val measureSpecHeight = MeasureSpec.makeMeasureSpec(baseWidth * 3 / 4, MeasureSpec.EXACTLY)
                                view.measure(measureSpecWidth, measureSpecHeight)
                            }
                            childLayoutParams.width < childLayoutParams.height -> { // 宽 < 高，1-2的适配方式
                                // 宽度是固定的
                                val measureSpecWidth = MeasureSpec.makeMeasureSpec(baseWidth, MeasureSpec.EXACTLY)
                                // 高度也是固定的，数值为宽度的4/3
                                val measureSpecHeight = MeasureSpec.makeMeasureSpec(baseWidth * 4 / 3, MeasureSpec.EXACTLY)
                                view.measure(measureSpecWidth, measureSpecHeight)
                            }
                            else -> { // 宽 = 高，1-3的适配方式
                                // 宽度是固定的
                                val measureSpecWidth = MeasureSpec.makeMeasureSpec(baseWidth, MeasureSpec.EXACTLY)
                                // 高度也是固定的，数值与宽度相等
                                val measureSpecHeight = MeasureSpec.makeMeasureSpec(baseWidth, MeasureSpec.EXACTLY)
                                view.measure(measureSpecWidth, measureSpecHeight)
                            }
                        }
                    } else {  // wiki中1-4的适配
                        val smallWidth = (defaultWidth - mInterval) / 2
                        // 宽度是固定的
                        val measureSpecWidth = MeasureSpec.makeMeasureSpec(smallWidth, MeasureSpec.EXACTLY)
                        // 高度也是固定的，数值与宽度相等
                        val measureSpecHeight = MeasureSpec.makeMeasureSpec(smallWidth, MeasureSpec.EXACTLY)
                        view.measure(measureSpecWidth, measureSpecHeight)
                    }
                }

                LogUtil.d("1图模式下的子view被测量：width=${view.measuredWidth} height=${view.measuredHeight}")
                // 自身高，就是子类高
                dimensionWidth = defaultWidth
                dimensionHeight = view.measuredHeight
            }
            2 -> { // 一行分2列，宽高固定
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
                val row = 1 // 最大有几行，从1开始
                val height = childWidth * row + mInterval * (row - 1).coerceAtLeast(0)
                dimensionWidth = defaultWidth
                dimensionHeight = height
            }
            4 -> { // 4图模式比较特殊，是9图取1，2，4，5
                // 宽高固定，且一样
                val childWidth = (defaultWidth - mInterval) / 3
                val measureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY)
                // 因为除不尽，导致尾部留1px没有填充问题，处理该问题
                children.forEach { view ->
                    view.measure(measureSpec, measureSpec)
                }
                // 自身宽高
                val row = 2 // 最大有几行，从1开始
                val height = childWidth * row + mInterval * (row - 1).coerceAtLeast(0)
                dimensionWidth = defaultWidth
                dimensionHeight = height
            }
            else -> { // 一行分3列，3*3类型，宽高固定
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
                val row = (mAdapter!!.getCount().coerceAtMost(MAX_SHOW_VIEW) + 2) / 3 // 最大有几行
                val height = childWidth * row + mInterval * (row - 1).coerceAtLeast(0)
                dimensionWidth = defaultWidth
                dimensionHeight = height
            }
        }
        // 不能小于最小值
        dimensionWidth = dimensionWidth.coerceAtLeast(getCustomSize(suggestedMinimumWidth, widthMeasureSpec))
        dimensionHeight = dimensionHeight.coerceAtLeast(getCustomSize(suggestedMinimumHeight, heightMeasureSpec))
        setMeasuredDimension(dimensionWidth, dimensionHeight)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // 如果adapter没设置，不需要布局
        mAdapter ?: return

        if (mAdapter!!.getCount() == 1) { // 只有一条数据的时候
            getChildAt(0)?.let {
                it.layout(0, 0, it.measuredWidth, it.measuredHeight)
            }
        } else if (mAdapter!!.getCount() == 2 || mAdapter!!.getCount() == 4) { // 一行分2列，宽高固定（2，4不是同一个固定值，但是不影响布局）
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

    override fun dispatchDraw(canvas: Canvas?) {
        canvas ?: return
        // 这里是为了处理截取后的背景色，让显示成背景的样色，不设置，默认截图出的部分是黑色
        canvas.saveLayer(RectF(0F, 0F, canvas.width.toFloat(), canvas.height.toFloat()), null)
        super.dispatchDraw(canvas)

        // 处理圆角的问题
        drawCircle(canvas)

        canvas.restore()
    }

    private fun drawCircle(canvas: Canvas) {
        // 除了1图模式，其它的宽、高都一样；高度取child at 0的高度;宽度最后一个可能因为小数点后的适配问题，需要取全部相加
        when (childCount) {
            0 -> {
            }
            1 -> {
                val view = getChildAt(0)
                drawTopLeft(canvas, PointF(0f, 0f), mRadius)
                drawTopRight(canvas, PointF(view.width.toFloat(), 0f), mRadius)
                drawBottomLeft(canvas, PointF(0f, view.height.toFloat()), mRadius)
                drawBottomRight(canvas, PointF(view.width.toFloat(), view.height.toFloat()), mRadius)
            }
            2 -> {
                val view0 = getChildAt(0)
                val view1 = getChildAt(1)
                val rowWidth = (view0.width + view1.width + mInterval).toFloat()
                val rowHeight = view0.height.toFloat()
                drawTopLeft(canvas, PointF(0f, 0f), mRadius)
                drawTopRight(canvas, PointF(rowWidth, 0f), mRadius)
                drawBottomLeft(canvas, PointF(0f, rowHeight), mRadius)
                drawBottomRight(canvas, PointF(rowWidth, rowHeight), mRadius)
            }
            3 -> {
                val view0 = getChildAt(0)
                val view1 = getChildAt(1)
                val view2 = getChildAt(2)
                val rowWidth = (view0.width + view1.width + view2.width + mInterval * 2).toFloat()
                val rowHeight = view0.height.toFloat()
                drawTopLeft(canvas, PointF(0f, 0f), mRadius)
                drawTopRight(canvas, PointF(rowWidth, 0f), mRadius)
                drawBottomLeft(canvas, PointF(0f, rowHeight), mRadius)
                drawBottomRight(canvas, PointF(rowWidth, rowHeight), mRadius)
            }
            4 -> {
                val view0 = getChildAt(0)
                val view1 = getChildAt(1)
                val rowWidth = (view0.width + view1.width + mInterval).toFloat()
                val rowHeight = (view0.height * 2 + mInterval).toFloat()
                drawTopLeft(canvas, PointF(0f, 0f), mRadius)
                drawTopRight(canvas, PointF(rowWidth, 0f), mRadius)
                drawBottomLeft(canvas, PointF(0f, rowHeight), mRadius)
                drawBottomRight(canvas, PointF(rowWidth, rowHeight), mRadius)
            }
            5 -> {
                val view0 = getChildAt(0)
                val view1 = getChildAt(1)
                val view2 = getChildAt(2)
                val rowWidth = (view0.width + view1.width + view2.width + mInterval * 2).toFloat()
                val rowHeight = view0.height.toFloat()
                drawTopLeft(canvas, PointF(0f, 0f), mRadius)
                drawTopRight(canvas, PointF(rowWidth, 0f), mRadius)
                drawBottomLeft(canvas, PointF(0f, rowHeight * 2 + mInterval), mRadius)
                drawBottomRight(canvas, PointF(rowWidth, rowHeight), mRadius)
                drawBottomRight(canvas, PointF((view0.width + view1.width + mInterval).toFloat(), rowHeight * 2 + mInterval), mRadius)
            }
            6 -> {
                val view0 = getChildAt(0)
                val view1 = getChildAt(1)
                val view2 = getChildAt(2)
                val rowWidth = (view0.width + view1.width + view2.width + mInterval * 2).toFloat()
                val rowHeight = (view0.height * 2 + mInterval).toFloat()
                drawTopLeft(canvas, PointF(0f, 0f), mRadius)
                drawTopRight(canvas, PointF(rowWidth, 0f), mRadius)
                drawBottomLeft(canvas, PointF(0f, rowHeight), mRadius)
                drawBottomRight(canvas, PointF(rowWidth, rowHeight), mRadius)
            }
            7 -> {
                val view0 = getChildAt(0)
                val view1 = getChildAt(1)
                val view2 = getChildAt(2)
                val rowWidth = (view0.width + view1.width + view2.width + mInterval * 2).toFloat()
                val rowHeight = view0.height.toFloat()
                drawTopLeft(canvas, PointF(0f, 0f), mRadius)
                drawTopRight(canvas, PointF(rowWidth, 0f), mRadius)
                drawBottomLeft(canvas, PointF(0f, rowHeight * 3 + mInterval * 2), mRadius)
                drawBottomRight(canvas, PointF(rowWidth, rowHeight * 2 + mInterval), mRadius)
                drawBottomRight(canvas, PointF((view0.width).toFloat(), rowHeight * 3 + mInterval * 2), mRadius)
            }
            8 -> {
                val view0 = getChildAt(0)
                val view1 = getChildAt(1)
                val view2 = getChildAt(2)
                val rowWidth = (view0.width + view1.width + view2.width + mInterval * 2).toFloat()
                val rowHeight = view0.height.toFloat()
                drawTopLeft(canvas, PointF(0f, 0f), mRadius)
                drawTopRight(canvas, PointF(rowWidth, 0f), mRadius)
                drawBottomLeft(canvas, PointF(0f, rowHeight * 3 + mInterval * 2), mRadius)
                drawBottomRight(canvas, PointF(rowWidth, rowHeight * 2 + mInterval), mRadius)
                drawBottomRight(canvas, PointF((view0.width + view1.width + mInterval).toFloat(), rowHeight * 3 + mInterval * 2), mRadius)
            }
            else -> {
                val view0 = getChildAt(0)
                val view1 = getChildAt(1)
                val view2 = getChildAt(2)
                val rowWidth = (view0.width + view1.width + view2.width + mInterval * 2).toFloat()
                val rowHeight = (view0.height * 3 + mInterval * 2).toFloat()
                drawTopLeft(canvas, PointF(0f, 0f), mRadius)
                drawTopRight(canvas, PointF(rowWidth, 0f), mRadius)
                drawBottomLeft(canvas, PointF(0f, rowHeight), mRadius)
                drawBottomRight(canvas, PointF(rowWidth, rowHeight), mRadius)
            }
        }
    }

    // 左上画圆角
    private fun drawTopLeft(canvas: Canvas, pointF: PointF, radius: Float) {
        val path = Path()
        path.moveTo(pointF.x, pointF.y + radius)
        path.lineTo(pointF.x, pointF.y)
        path.lineTo(pointF.x + radius, pointF.y)
        path.arcTo(RectF(pointF.x, pointF.y, pointF.x + radius * 2, pointF.y + radius * 2), -90f, -90f)
        path.close()
        canvas.drawPath(path, mRoundPaint)
    }

    // 右上画圆角
    private fun drawTopRight(canvas: Canvas, pointF: PointF, radius: Float) {
        val path = Path()
        path.moveTo(pointF.x, pointF.y + radius)
        path.lineTo(pointF.x, pointF.y)
        path.lineTo(pointF.x - radius, pointF.y)
        path.arcTo(RectF(pointF.x - radius * 2, pointF.y, pointF.x, pointF.y + radius * 2), -90f, 90f)
        path.close()
        canvas.drawPath(path, mRoundPaint)
    }

    // 左下画圆角
    private fun drawBottomLeft(canvas: Canvas, pointF: PointF, radius: Float) {
        val path = Path()
        path.moveTo(pointF.x, pointF.y - radius)
        path.lineTo(pointF.x, pointF.y)
        path.lineTo(pointF.x + radius, pointF.y)
        path.arcTo(RectF(pointF.x, pointF.y - radius * 2, pointF.x + radius * 2, pointF.y), 90f, 90f)
        path.close()
        canvas.drawPath(path, mRoundPaint)
    }

    // 右下画圆角
    private fun drawBottomRight(canvas: Canvas, pointF: PointF, radius: Float) {
        val path = Path()
        path.moveTo(pointF.x, pointF.y - radius)
        path.lineTo(pointF.x, pointF.y)
        path.lineTo(pointF.x - radius, pointF.y)
        path.arcTo(RectF(pointF.x - radius * 2, pointF.y - radius * 2, pointF.x, pointF.y), 90f, -90f)
        path.close()
        canvas.drawPath(path, mRoundPaint)
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