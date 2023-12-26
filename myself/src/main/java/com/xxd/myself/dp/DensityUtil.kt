package com.xxd.myself.dp

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import com.xxd.common.base.BaseApplication

/**
 *    author : xxd
 *    date   : 2023/12/26
 *    desc   : 修改DisplayMetrics来适配
 */
object DensityUtil {

    private var mCompatDensity = 0f
    private var mCompatScaledDensity = 0f
    private const val mDesignWidth = 375f // 设置图的width，dp

    /**
     * 该方法应该在测量Activity之前调用，才能对当前Activity起作用，否则只能对之后的页面启作用
     */
    fun initCustomDensity(activity: Activity, application: Application) {
        val displayMetrics = application.resources.displayMetrics
        if (mCompatDensity == 0f) {
            mCompatDensity = displayMetrics.density
            mCompatScaledDensity = displayMetrics.scaledDensity
            // 监听外部字体变化
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration) {
                    if (newConfig.fontScale > 0) {
                        mCompatScaledDensity = BaseApplication.application.resources.displayMetrics.scaledDensity
                    }
                }

                override fun onLowMemory() {
                }
            })
        }

        // 按照设计图适配
        val targetDensity = displayMetrics.widthPixels / mDesignWidth
        val targetScaledDensity = targetDensity * mCompatScaledDensity / mCompatDensity
        val targetDensityDpi = (targetDensity * 160).toInt()
        // 按照自己的设计图赋值，这里赋值对当前activity无用
        displayMetrics.apply {
            density = targetDensity
            scaledDensity = targetScaledDensity
            densityDpi = targetDensityDpi
        }
        // 必须拿到Activity的displayMetrics赋值才有用
        activity.resources.displayMetrics.apply {
            density = targetDensity
            scaledDensity = targetScaledDensity
            densityDpi = targetDensityDpi
        }
    }
}