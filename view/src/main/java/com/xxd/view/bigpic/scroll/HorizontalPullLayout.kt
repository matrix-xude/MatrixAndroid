package com.xxd.view.bigpic.scroll

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import androidx.core.view.children
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.xxd.view.R
import com.xxd.view.databinding.ViewHeadHorizontalLeftBinding

/**
 *    author : xxd
 *    date   : 2022/8/17
 *    desc   : 本布局实现了，当左滑超过界限后，继续左滑，拉出右边的抽屉布局，释放可以拿到回调监听
 *              1. 布局内部只获取第一个子View,用来measure、layout
 *              2. 因为本类通过 NestedScrollingParent 实现，所有包裹类必须是 NestedScrollingChild才有效（如：RecycleView、ViewPager2）
 *              3. 本来只检测横滑的事件，竖直滑动的RecycleView等不会响应拉出右边布局的事件
 */
class HorizontalPullLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewGroup(context, attributeSet, defStyleAttr), NestedScrollingParent {

    // 右边横滑到底后，继续滑动出现的UI
    private val rightViewBinding: ViewHeadHorizontalLeftBinding

    init {
        rightViewBinding = ViewHeadHorizontalLeftBinding.inflate(LayoutInflater.from(context), this, false)
        addView(rightViewBinding.root)
    }

    /*************** 外部可以赋值修改 ********************/
    var needHorizontalRefresh = false // 是否需要开启，横滑到最后，继续滑出刷新头

    var pullDesc = "继续滑动查看全部内容"   // 滑动刚开始出现的文字
        set(value) {
            rightViewBinding.tvDesc.text = value
            field = value
        }

    var releaseDesc = "释放查看全部内容"  // 滑动超过一定距离，触发释放监听展示的文字

    var releaseListener: (() -> Unit)? = null // 滑动超过一段距离释放的监听

    /*************** 外部可以赋值修改end ********************/

    private var isDealScroll = false  // 是否开次本次镶嵌滑动处理
    private var offSetX = 0  // x轴多余的偏移量

    // 移动此View,响应
    private fun scrollThisView() {
        val maxWidth = rightViewBinding.root.width
        if (maxWidth > 0) {
            offSetX = offSetX.coerceAtMost(maxWidth)
            if (offSetX < maxWidth * 2 / 3) {
                rightViewBinding.iconArrow.text = resources.getString(R.string.icon_629)
                rightViewBinding.tvDesc.text = pullDesc
            } else {
                rightViewBinding.iconArrow.text = resources.getString(R.string.icon_617)
                rightViewBinding.tvDesc.text = releaseDesc
            }
        }
        scrollTo(offSetX, 0)
        Logger.d("本布局的 translationX=$translationX  scrollX=$scrollX")
    }

    private fun resetScroll() {
        // 检测是否需要影响回调
        val maxWidth = rightViewBinding.root.width
        if (maxWidth > 0) {
            if (offSetX >= maxWidth * 2 / 3) {
                releaseListener?.invoke()
            }
        }
        offSetX = 0
        isDealScroll = false // 结束本次事件
        scrollTo(offSetX, 0)
    }


    private val helper = NestedScrollingParentHelper(this)

    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        // 只有需要刷新 and 本次开始是横滑事件，才开始嵌套滑处理
        if (needHorizontalRefresh && axes == ViewCompat.SCROLL_AXIS_HORIZONTAL) {
            isDealScroll = true
        }
        return needHorizontalRefresh
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        helper.onNestedScrollAccepted(child, target, axes)
    }

    override fun onStopNestedScroll(target: View) {
        helper.onStopNestedScroll(target)
        resetScroll()
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        if (isDealScroll && dxUnconsumed > 0) {
            offSetX += dxUnconsumed
            scrollThisView()
        }
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        // 已经左滑显示出了"释放部分"
        if (isDealScroll && offSetX > 0) {
            // 不将事件传给子NestedScrollChild，自己消费
            offSetX += dx
            if (offSetX < 0) {
                consumed[0] = dx - offSetX
                consumed[1] = dy
                offSetX = 0
            } else {
                consumed[0] = dx
                consumed[1] = dy
            }
            scrollThisView()
        }

    }

    // 不处理Fling事件，否则会导致内部的ViewPager2拿不到Fling
    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return false
    }

    // 不处理Fling事件，否则会导致内部的ViewPager2拿不到Fling
    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    // 用不到
    override fun getNestedScrollAxes(): Int {
        return helper.nestedScrollAxes
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 右边的View
        findRightView()?.let { rightView ->
            val measureSpecWidth0 = getChildMeasureSpec(widthMeasureSpec, 0, rightView.layoutParams.width)
            val measureSpecHeight0 = getChildMeasureSpec(heightMeasureSpec, 0, rightView.layoutParams.height)
            rightView.measure(measureSpecWidth0, measureSpecHeight0)
        }
        findTargetView()?.let {
            // 宽、高由子类自己测量
            val measureSpecWidth = getChildMeasureSpec(widthMeasureSpec, 0, it.layoutParams.width)
            val measureSpecHeight = getChildMeasureSpec(heightMeasureSpec, 0, it.layoutParams.height)
            it.measure(measureSpecWidth, measureSpecHeight)
            // 子类测量完毕，父类直接由该子类置顶宽高
            val dimensionWidth = it.measuredWidth.coerceAtLeast(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec))
            val dimensionHeight = it.measuredHeight.coerceAtLeast(getDefaultSize(suggestedMinimumHeight, heightMeasureSpec))
            setMeasuredDimension(dimensionWidth, dimensionHeight)
        } ?: setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec), getDefaultSize(suggestedMinimumHeight, heightMeasureSpec))


    }

    // 找到ViewGroup包裹的View,不是该类右边加的即为找到
    private fun findTargetView(): View? {
        children.forEach {
            if (it != rightViewBinding.root)
                return it
        }
        return null
    }

    // 找到右边的View
    private fun findRightView(): View? {
        children.forEach {
            if (it == rightViewBinding.root)
                return it
        }
        return null
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        findTargetView()?.let {
            it.layout(0, 0, it.measuredWidth, it.measuredHeight)
        }
        findRightView()?.let {
            it.layout(right, 0, right + it.measuredWidth, it.measuredHeight)
        }
    }

}