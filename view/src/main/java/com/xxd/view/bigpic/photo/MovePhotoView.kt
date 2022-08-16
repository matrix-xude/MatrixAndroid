package com.xxd.view.bigpic.photo

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.orhanobut.logger.Logger
import kotlin.math.absoluteValue

/**
 *    author : xxd
 *    date   : 2022/8/16
 *    desc   : 模仿微信的大图，效果参考微信
 *    1. 当图片没有进行缩放，并且已经处于顶部，继续下滑，图片会根据手势滑动，并且有缩放效果
 *    2. 当下滑一定距离后，会对外部进行回调（一般外部会变的透明，露出上一个Activity）
 *    3. 松手后，如果图片位置处于原图之下，并且超过一段距离（该Activity会有消失的需求），回调外部
 */
class MovePhotoView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    PhotoView(context, attributeSet, defStyleAttr) {

    /****** 外部可以赋值的变量 *******/
    // 移动多少距离后，该页面增加消失的回调，单位px
    var moveDragDistance = 300

    // 外部可以传入的监听事件
    var movePhotoListener: MovePhotoListener? = null

    /****** 外部可以赋值的变量 end *******/

    interface MovePhotoListener {

        // 取消拖拽成功（松手的回调，外部一般都是finish当前Activity）
        fun moveUp()

        // 取消拖拽中(外部一般是+-当前Activity的透明度等)
        fun moving(dx: Float, dy: Float)

        // 单击事件,外部不能设置onClick，因为内部有双击事件，应该使用此回调（外部一般是finish当前Activity）
        fun click()

        // 长按事件（外部一般是pop弹框，展示"保存图片"功能）
        fun longClick()
    }

    // 当前是否处于拖拽中
    private var isMoveDrag = false
    private var dragOffsetX = 0f  // 当前拖拽的x轴总偏移量
    private var dragOffsetY = 0f  // 当前拖拽的y轴总偏移量

    init {
        // 默认使用该缩放比例，外部可以重新设置
        setScaleLevels(1f, 2f, 5f)

        initDragListener()
        initUpListener()
        initOtherListener()
    }

    private fun initOtherListener() {
        setOnViewTapListener { _, _, _ ->
            movePhotoListener?.click()
        }
        this.setOnLongClickListener {
            movePhotoListener?.longClick()
            true
        }
    }

    private fun initUpListener() {
        attacher.mOnViewUpCancelListener = object : OnViewUpCancelListener {
            override fun onViewUpCancel() {
                if (!isMoveDrag)  // 不是拖拽模式，不处理
                    return
                if (dragOffsetY > moveDragDistance) {  // 达到了该页面消失的标准
                    movePhotoListener?.moveUp()
                } else {  // 没达到页面消失标准，重置图片matrix
                    resetMoveDrag()
                    invalidate()
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

        if (isMoveDrag) {
            val saveCount = canvas.saveCount
            canvas.translate(dragOffsetX, dragOffsetY)
            if (dragOffsetY > 0 && width > 0 && height > 0) {
                val scale = 1 - dragOffsetY / height
                canvas.scale(scale, scale, width.toFloat() / 2, height.toFloat() / 2)
            }
            super.onDraw(canvas)
            canvas.restoreToCount(saveCount)
        } else
            super.onDraw(canvas)
    }

    // 拿到父类的监听做，开启本类的拖拽操作
    private fun initDragListener() {
        setOnViewDragListener { dx, dy ->
            if (isMoveDrag) {  // 当前正处于拖拽中
                dragOffsetX += dx
                dragOffsetY += dy
                invalidate()
                movePhotoListener?.moving(dragOffsetY, dragOffsetY)
            } else {  // 当前不属于拖拽，判断是否能开始拖拽
                if (checkStartMoveDrag(dx, dy))
                    startMoveDrag(dx, dy)
            }
        }
    }

    // 检测当前是否能开始拖拽，仅仅是检测是否能开启，开启之后不需要满足这些条件
    private fun checkStartMoveDrag(dx: Float, dy: Float): Boolean {
        if (scale > minimumScale)  // 处于缩放时，不能开启拖拽
            return false
        if (attacher.isMorePointEvent) // 本次touch多指触碰，不能开启拖拽
            return false
        if (dx.absoluteValue >= dy.absoluteValue)  // x轴移动比y轴高，不能开启拖拽
            return false
        if (dy <= 0)  // 向上滑动，不能开启拖拽
            return false
        if (attacher.isLongPicMode && displayRect.top < 0) // 长图模式下，还未移动到顶部，不能开启拖拽
            return false
        return true
    }

    private fun startMoveDrag(dx: Float, dy: Float) {
        isMoveDrag = true
        dragOffsetX = dx
        dragOffsetY = dy
        attacher.isMoveDrag = true
    }

    // 重置此次的拖拽
    private fun resetMoveDrag() {
        isMoveDrag = false
        dragOffsetX = 0f
        dragOffsetY = 0f
        attacher.isMoveDrag = false
    }
}