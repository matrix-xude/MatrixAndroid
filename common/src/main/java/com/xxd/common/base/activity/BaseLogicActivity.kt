package com.xxd.common.base.activity

import com.alibaba.android.arouter.launcher.ARouter

/**
 *    author : xxd
 *    date   : 2021/7/5
 *    desc   : 存放必须放在base中的逻辑，一般是与生命周期相关的逻辑，
 *    与生命周期无关的逻辑可以直接放到工具类中
 */
abstract class BaseLogicActivity : BaseActivity(), IBaseLogic {

    override fun initView() {
        super.initView()
        lifecycle.addObserver(BaseLogicObserver(this))
    }

    override fun registerARouter() {
        ARouter.getInstance().inject(this)
    }
}