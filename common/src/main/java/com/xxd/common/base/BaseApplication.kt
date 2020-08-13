package com.xxd.common.base

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.xxd.common.BuildConfig


/**
 *    author : xxd
 *    date   : 2020/8/12
 *    desc   : 公共的Application，所有的module使用
 */
class BaseApplication : Application() {

    companion object {
        lateinit var application: BaseApplication

        const val LOG_TAG = "xxd"
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
        initLogger()
    }

    /**
     * 初始化logger日志
     */
    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(0)
            .methodOffset(5)
            .tag(LOG_TAG)
            .build()
        val adapter = object : AndroidLogAdapter(formatStrategy){
            // 只有debug模式下才打印日志
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        }
        Logger.addLogAdapter(adapter)
    }

    /**
     * 初始化阿里路由
     */
    private fun initARouter() {
        // debug模式下打开每次重新编译，否则可能有缓存导致加载不了新的annotation
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(application)
    }
}