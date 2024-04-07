package com.xxd.service.service

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.orhanobut.logger.Logger
import com.xxd.common.service.CommonRemoteService
import com.xxd.service.MyFirstDo

class RemoteService : Service() {

    private val binder by lazy {
        object : MyFirstDo.Stub() {
            override fun getInfo(): String {
                Logger.d("收到了远程请求 getInfo()")
                return "第一个Remote服务"
            }

            override fun add(a: Int, b: Int): Int {
                Logger.d("收到了远程请求 add(a: Int, b: Int)")
                return a + b
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        CommonRemoteService()
        Logger.d("RemoteService -> onBind")
        return binder
    }

    override fun unbindService(conn: ServiceConnection) {
        Logger.d("RemoteService -> unbindService")
        super.unbindService(conn)
    }

    override fun onCreate() {
        Logger.d("RemoteService -> onCreate")
        super.onCreate()
    }

    override fun onDestroy() {
        Logger.d("RemoteService -> onDestroy")
        super.onDestroy()
    }
}