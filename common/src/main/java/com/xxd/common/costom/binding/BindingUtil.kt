package com.xxd.common.costom.binding

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

/**
 *    author : xxd
 *    date   : 2021/7/13
 *    desc   :
 */
object BindingUtil {

    /**
     * 反射调用 inflate 方法
     */
    inline fun <reified T : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
        T::class.java.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, layoutInflater) as T
}