package com.xxd.myself

import android.os.Bundle
import com.orhanobut.logger.Logger
import com.xxd.common.base.BaseTitleActivity
import com.xxd.common.extend.binding
import com.xxd.myself.databinding.MyselfActivityMainBinding

class MainActivity : BaseTitleActivity() {

    private val myselfBinding: MyselfActivityMainBinding by binding(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("我被初始化了")
    }

    override fun initView() {
        super.initView()
    }

    override fun getTitleName(): CharSequence? {
        return "随心所欲"
    }



    override fun initData() {
        super.initData()
        titleBinding.tvTitleName.text = "测试222"
        myselfBinding.root


    }

}