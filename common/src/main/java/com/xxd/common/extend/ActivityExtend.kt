package com.xxd.common.extend

import android.app.Activity
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.xxd.common.costom.binding.BindingUtil

/**
 *    author : xxd
 *    date   : 2021/7/6
 *    desc   :
 */

/**
 * ViewBinding扩展，懒加载，调用的时候再setContentView()
 */
inline fun <reified T : ViewBinding> Activity.binding() = lazy {
    BindingUtil.inflateBinding<T>(layoutInflater).apply { setContentView(root) }
}

