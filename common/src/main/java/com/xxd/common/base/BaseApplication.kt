package com.xxd.common.base

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.xxd.common.BuildConfig


/**
 *    author : xxd
 *    date   : 2020/8/12
 *    desc   : 公共的Application，所有的module使用
 */
class BaseApplication : Application() {

    companion object {
        lateinit var application: BaseApplication
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        application = this
        // 小于5.0才需要执行此分包方法
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            MultiDex.install(this)
        }
    }

    override fun onCreate() {
        super.onCreate()

        initARouter()
    }

    /**
     * 初始化阿里路由
     */
    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(application)
    }
}