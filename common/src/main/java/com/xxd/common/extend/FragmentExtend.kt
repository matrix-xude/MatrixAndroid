package com.xxd.common.extend

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import java.lang.Exception
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *    author : xxd
 *    date   : 2021/7/6
 *    desc   :
 */

/**
 * Fragment扩展函数，ViewBinding
 */
inline fun <reified T : ViewBinding> Fragment.binding(): FragmentBindingDelegate<T> =
    FragmentBindingDelegate()

/**
 * Fragment委托类，需要在onDestroyView中销毁ViewBinding
 */
class FragmentBindingDelegate<T : ViewBinding> :
    ReadWriteProperty<Fragment, T> {
    // 因为可以被销毁，所以需要加上?
    private var viewBinding: T? = null
    // 只用来获取一个不为nul的值，方便调用
    private val binding
        get() = viewBinding!!

    // 是否已经注册onViewDestroy的监听
    private var isRegisterDestroy = false

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {

        if (!isRegisterDestroy) {
            // 在view创建前是拿不到viewLifecycleOwner的，会抛出异常，一般不会出现，只有错误调用的时候可能出现
            try {
                thisRef.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                    fun destroyViewBinding() {
                        viewBinding = null
                        isRegisterDestroy = false // 销毁View,需要重新注册
                    }
                })
                isRegisterDestroy = true // 只要注册一次即可
            } catch (e: Exception) {
            }
        }
        return binding
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        viewBinding = value
    }
}