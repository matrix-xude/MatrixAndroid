package com.xxd.service.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.orhanobut.logger.Logger

/**
 *    author : xxd
 *    date   : 2024/4/5
 *    desc   :
 */
class FirstService : Service() {

    // 实现 IBinder 的方式
    inner class FirstBinder : Binder(){
        fun getService(): FirstService {
            return this@FirstService
        }
    }

    private val myBind by lazy {
        FirstBinder()
    }

    override fun onCreate() {
        super.onCreate()
        Logger.d("FirstService -> onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("FirstService -> onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logger.d("FirstService -> onStartCommand flags=$flags startId=$startId")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Logger.d("FirstService -> onBind")
        return myBind
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Logger.d("FirstService -> onUnbind")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Logger.d("FirstService -> onRebind")
        super.onRebind(intent)
    }

    // 创建一些外部可以调用方法
    fun logFirstService(){
        Logger.d("通过 IBind 调用了 FirstService 内部的方法")
    }
}