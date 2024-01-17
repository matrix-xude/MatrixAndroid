package com.xxd.myself.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 *    author : xxd
 *    date   : 2024/1/7
 *    desc   :
 */
class SeekLifeService : Service() {


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    // startService才会执行该方法
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    // onCreate() onDestroy() 是2种开启服务的方式共有的生命周期
    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}