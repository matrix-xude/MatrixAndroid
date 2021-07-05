package com.xxd.common.base.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 *    author : xxd
 *    date   : 2020/8/13
 *    desc   : BaseActivity,不做任何逻辑处理,只确定Activity的结构（可移植到任意app）
 *    1. 主动调用了setContentView()，由子类提供getContentViewBase(),需要的是View类型，不是layoutId,方便使用ViewBinding
 *    2. 提供一个initBeforeContentView（）的空实现,可以覆写
 *    3. 提供一个initView（）的空实现,可以覆写
 *    4. 提供一个initData（）的空实现,可以覆写
 */
abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBeforeContentView()
        setContentView(getContentViewBase())
        initView()
        initData()

    }

    abstract fun getContentViewBase(): View

    /**
     * 一些需要在setContentView()之前初始化的代码
     */
    open fun initBeforeContentView(){}

    open fun initView() {
    }

    open fun initData() {
    }

}