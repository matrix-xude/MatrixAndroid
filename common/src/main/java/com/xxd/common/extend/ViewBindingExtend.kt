package com.xxd.common.extend

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import com.orhanobut.logger.Logger
import java.lang.Exception
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
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
 * @param setContextView 是否调用Activity的 setContextView()方法
 */
inline fun <reified T : ViewBinding> Activity.binding(setContextView: Boolean = true) = lazy {
    Logger.d("Activity.binding 被调用")
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

/**
 * Fragment扩展函数，ViewBinding
 */
inline fun <reified T : ViewBinding> Fragment.binding(): FragmentBindingDelegate<T> =
    FragmentBindingDelegate()


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

/**
 * 因为Fragment需要在onViewDestroy()方法中销毁ViewBinding,所以不能用lazy(),
 * 而应该主动调用销毁，onCreateView()的时候再次创建ViewBinding
 */
class FragmentBindingDelegateDemo<T : ViewBinding>(private val viewBindingClazz: Class<T>) :
    ReadOnlyProperty<Fragment, T> {
    // 因为可以被销毁，所以需要加上?
    private var viewBinding: T? = null

    // 真正返回的T,只要调用时机不在onViewCreate()之前或者onViewDestroy()之后都不会报错
    private val binding: T
        get() = viewBinding!!

    // 是否已经注册onViewDestroy的监听
    private var isRegisterDestroy = false

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {

        if (viewBinding == null) {
            viewBinding = viewBindingClazz.getMethod(
                "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
            ).invoke(null, thisRef.layoutInflater, LinearLayout(thisRef.context), false) as T
            Logger.d("${thisRef.javaClass.simpleName}的ViewBinding创建成功")
        }

        if (!isRegisterDestroy) {
            // 在view创建前是拿不到viewLifecycleOwner的，会抛出异常
            try {
                thisRef.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                    fun destroyViewBinding() {
                        viewBinding = null
                        isRegisterDestroy = false // 销毁View,需要重新注册
                        Logger.d("${thisRef.javaClass.simpleName}的ViewBinding被销毁了")
                    }
                })
                isRegisterDestroy = true // 只要注册一次即可
            } catch (e: Exception) {
                Logger.d("${thisRef.javaClass.simpleName}第一次注册ViewBinding销毁的时候，获取viewLifecycleOwner失败：${e.message}")
            }
        }
        return binding
    }
}

