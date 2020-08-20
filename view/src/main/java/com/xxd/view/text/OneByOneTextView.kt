package com.xxd.view.text

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 *    author : xxd
 *    date   : 2020/8/20
 *    desc   : 展示的字一个一个显示出来的TextView，设置lifecycle性能更佳
 */
class OneByOneTextView : AppCompatTextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        // 考虑view生命周期问题,ScheduledExecutorService可能比当前界面生命周期长
        addOnAttachStateChangeListener(object : OnAttachStateChangeListener {

            override fun onViewDetachedFromWindow(v: View?) {
                isViewAttachedToWindow = false
                shutdownScheduledExecutorService()
            }

            override fun onViewAttachedToWindow(v: View?) {
                isViewAttachedToWindow = true
                // 如果挂载的时候已经显示完全部，需要重新设置,判null是因为可以当做普通textView使用
                if (subText != null)
                    refreshSubText()
            }
        })
    }

    /**
     * 当前view是否被挂载到显示界面上，没有的时候不需要刷新文字
     */
    private var isViewAttachedToWindow = false

    /**
     * 当前view是否活跃，即当前view的加载activity或者fragment是否为onStart状态
     */
    private var isViewActive = true

    /**
     * 轮询器
     */
    private var scheduledExecutorService: ScheduledExecutorService? = null

    /**
     * 外部设置进来的完整内容，默认为null
     */
    private var completeText: CharSequence? = null

    /**
     * 当前显示的截取部分文字
     */
    private var subText: CharSequence? = null

    /**
     * 当前轮询的间隔，毫秒
     */
    private var period = 100L

    /**
     * 设置一个一个显示的文字
     * @param text 内容文字
     * @param period 每个文字显示的间隔时间，单位：毫秒
     */
    fun setOneByOneText(text: CharSequence?, period: Long = 100) {
        shutdownScheduledExecutorService() // 每次设置文字都停止上一次的显示

        // text == null这样判断存在才能使得下面text不需要!!判断
        if (text == null || text.isEmpty()) {
            this.completeText = text
            this.subText = null
            this.text = text
            return
        }
        this.period = period
        this.completeText = text
        this.subText = null

        startScheduled(text, period)
    }

    /**
     * 重新显示一遍上次设置的文字
     */
    fun resetText() {
        setOneByOneText(completeText, period)
    }

    /**
     * 取消当前的循环显示
     */
    fun stopOneByOneText() {
        shutdownScheduledExecutorService()
    }

    /**
     * 设置lifecycle会更加节约性能，当前view不可见的时候，不会刷新文字
     * @param lifecycle 一般是activity 或者 fragment 的lifecycle
     */
    fun setLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(object : LifecycleObserver {

            // 适配fragment，用ON_RESUME
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun isActive() {
                isViewActive = true
                refreshSubText()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun noActive() {
                isViewActive = false
            }
        })
    }

    /**
     * 开始轮询
     */
    private fun startScheduled(text: CharSequence, period: Long) {

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        var showCount = 0 // 已经显示的文字数
        scheduledExecutorService?.scheduleAtFixedRate({
            showCount++
            if (showCount > text.length) {
                shutdownScheduledExecutorService()
                return@scheduleAtFixedRate
            }
            subText = text.subSequence(0, showCount)
            refreshSubText()
        }, 0, period, TimeUnit.MILLISECONDS)
    }

    // 刷新当前截取到的文字部分
    private fun refreshSubText() {
        if (isViewAttachedToWindow && isViewActive) {
            post {
                this.text = subText
            }
        }
    }

    private fun shutdownScheduledExecutorService() {
        scheduledExecutorService?.shutdown()
        scheduledExecutorService = null
    }


}