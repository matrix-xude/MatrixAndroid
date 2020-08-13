package com.xxd.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *    author : xxd
 *    date   : 2020/8/13
 *    desc   : 通用的 BaseActivity,暂时只有最基础功能，等待扩充
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        initData()
    }

    /**
     * 获取layout的id
     */
    abstract fun getLayoutId(): Int

    open fun initView() {
        // kotlin中直接使用id,但是找不到其它module中的id,需要手动获取
    }

    open fun initData() {
    }

}