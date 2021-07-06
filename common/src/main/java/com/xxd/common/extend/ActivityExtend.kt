package com.xxd.common.extend

import android.app.Activity
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

/**
 *    author : xxd
 *    date   : 2021/7/6
 *    desc   :
 */

/**
 * ViewBinding扩展，懒加载，调用的时候再setContentView()
 */
inline fun <reified T : ViewBinding> Activity.binding() = lazy {
    inflateBinding<T>(layoutInflater).apply { setContentView(root) }
}

/**
 * 内联+具体泛型，一次反射调用 inflate 方法
 */
inline fun <reified T : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
    T::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, layoutInflater) as T