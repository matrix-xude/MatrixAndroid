package com.xxd.common.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.xxd.common.R
import com.xxd.common.init.ActivityRegisterImpl
import com.xxd.common.init.IActivityRegister

/**
 *    author : xxd
 *    date   : 2020/8/13
 *    desc   : 通用的 BaseActivity,此类只进行基础的封装操作，不处理UI相关的内容
 *
 */
abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        rootView = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
        initRegister()
        initView()
        initData()
    }

    /**
     * 获取ContentView，适配的是ViewBinding
     */
    abstract fun getContentView(): View

    private fun initRegister() {
        getRegister()?.register(this)
    }

    /**
     * 获取需要注册的内容，不同app换成自己的业务逻辑
     */
    private fun getRegister(): IActivityRegister? {
        return ActivityRegisterImpl()
    }

    open fun initView() {
        // kotlin中直接使用id,但是找不到其它module中的id,需要手动获取
    }

    open fun initData() {
    }

}