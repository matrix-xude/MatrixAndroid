package com.xxd.common.extend

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * author : xxd
 * date   : 2021/6/21
 * desc   : ViewBinding的扩展，方便使用ViewBinding
 * 优点：
 * 1. 无侵入式，不需要继承base类
 * 2. lazy{} 使用的时候初始化，保证永远不会出现null
 * 缺点：
 * 1. 使用了1次反射，但不是太影响效率
 * 2. 必须调用1次viewBinding的控件才会加载页面
 */

/**
 * Activity的扩展函数,ViewBinding调用
 * @param setContextView 是否调用Activity的 setContextView()方法，正常都需要调用，某些base类中有不调用的需求
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
 */
inline fun <reified T : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
    T::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, layoutInflater) as T

//inline fun <reified T : ViewBinding> Fragment.binding()

class FragmentBindingDelegate<T : ViewBinding>(private val clazz :Class<T>) : ReadOnlyProperty<Fragment,T>{
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        TODO("Not yet implemented")
    }

}

