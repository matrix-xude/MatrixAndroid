package com.xxd.common.extend

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.orhanobut.logger.Logger

/**
 * author : xxd
 * date   : 2021/6/21
 * desc   : ViewBinding的扩展，方便使用ViewBinding
 * 特点：
 * 1. 无侵入式，不需要继承base类
 * 2. 使用了1次反射，并不是太影响效率
 * 3. lazy{} 使用的时候初始化，保证永远不会出现null
 */

/**
 * Activity的扩展函数,ViewBinding调用
 */
inline fun <reified T : ViewBinding> Activity.binding(setContextView: Boolean = true) = lazy {
    inflateBinding<T>(layoutInflater).apply { if (setContextView) setContentView(root) }
}

/**
 * Dialog的扩展函数,ViewBinding调用
 */
inline fun <reified T : ViewBinding> Dialog.binding() = lazy {
    inflateBinding<T>(layoutInflater).apply { setContentView(root) }
}

/**
 * 内联+具体泛型，一次反射调用 inflate 方法
 * 如果不需要调用 setContentView()，可以调用此方法
 */
inline fun <reified T : ViewBinding> inflateBinding(layoutInflater: LayoutInflater): T {
    Logger.d("${T::class.java.name}")
    val t = T::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, layoutInflater) as T
    Logger.d("${T::class.java.name}")
    return t
}

