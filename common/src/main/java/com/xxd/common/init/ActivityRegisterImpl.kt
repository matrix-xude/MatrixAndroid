package com.xxd.common.init

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter

/**
 *    author : xxd
 *    date   : 2020/8/27
 *    desc   : 需要在 BaseActivity 注册的内容
 */
class ActivityRegisterImpl : IActivityRegister {

    /**
     * 本app业务逻辑需要在 BaseActivity 里初始化的内容
     */
    override fun register(context: Context) {

        // 阿里路由解析跳转参数
        ARouter.getInstance().inject(context)

    }
}