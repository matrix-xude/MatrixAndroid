package com.xxd.common.costom.binding.helper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import java.lang.reflect.ParameterizedType

/**
 *    author : xxd
 *    date   : 2021/7/13
 *    desc   : 扩展 BaseQuickAdapter 为创建了ViewBinding的类
 *    1. 因为该类抽象，子类能获取到父类的泛型 VB,所有可以直接创建该类的子类，该类中反射获取泛型调用 VB.inflater
 *    2. 如果需要层级继承该类，那么可能导致不能直接获取到 VB 的真实类型，一旦获取不到，提供一个兜底方法，手动调用 VB.inflater
 */
abstract class BaseBindingQuickAdapter<T, VB : ViewBinding>(
    data: MutableList<T>? = null
) : BaseQuickAdapter<T, BaseBindingViewHolder<VB>>(0, data) {

    // 用子类获取到当前类的class，可能找不到
    private var viewBindingClass: Class<*>? = null

    // 找到反射的类，只要执行一次就够了
    private val reflectMethod by lazy {
        viewBindingClass?.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
    }

    // 初始化的时候就反射找该VB的真实class类型
    init {
        // 此类为abstract，必定能找到某层找到该类
        var javaClass: Class<*> = this.javaClass
        while (javaClass.superclass != BaseBindingQuickAdapter::class.java) {
            javaClass = javaClass.superclass!!
        }
        val type = javaClass.genericSuperclass
        type as ParameterizedType // 必然可以转换，就是该类
        val type1 = type.actualTypeArguments[1]
        if (type1 is Class<*>) {
            viewBindingClass = type1
        }
    }

    // 覆写父类创建ViewHolder的方法
    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<VB> {
        val vb = reflectMethod?.invoke(null, LayoutInflater.from(context), parent, false)?.let { it as VB }
            ?: reflectNullViewHolder(LayoutInflater.from(context))
            ?: throw NullPointerException("当前反射找不到VB : ViewBinding的泛型,请覆写 getHolder(layoutInflater: LayoutInflater) 方法")
        return BaseBindingViewHolder(vb)
    }

    // 兜底方法，反射获取不到class时，调用此方法获取VIewHolder
    open fun reflectNullViewHolder(layoutInflater: LayoutInflater): VB? {
        return null
    }
}