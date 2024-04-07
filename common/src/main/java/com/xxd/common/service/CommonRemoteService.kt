package com.xxd.common.service

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.PointF
import android.os.IBinder
import com.orhanobut.logger.Logger
import com.xxd.common.CommonFirst
import com.xxd.common.service.domain.CommonPoint

class CommonRemoteService : Service() {

    private val binder by lazy {
        object : CommonFirst.Stub() {
            override fun getInfo(): String {
                Logger.d("收到了远程请求 getInfo()")
                return "first Remote 服务"
            }

            override fun add(a: Int, b: Int): Int {
                Logger.d("remote add(a: Int, b: Int)")
                return a + b
            }

            override fun point(a: Int, b: Int): PointF {
                return PointF(a.toFloat(), b.toFloat())
            }

            override fun myPoint(a: Int, b: Int): CommonPoint {
                return CommonPoint(a, b)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        Logger.d("CommonRemoteService -> onBind")
        return binder
    }

    override fun unbindService(conn: ServiceConnection) {
        Logger.d("CommonRemoteService -> unbindService")
        super.unbindService(conn)
    }

    override fun onCreate() {
        Logger.d("CommonRemoteService -> onCreate")
        super.onCreate()
    }

    override fun onDestroy() {
        Logger.d("CommonRemoteService -> onDestroy")
        super.onDestroy()
    }
}