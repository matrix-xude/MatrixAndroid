package com.xxd.myself.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.PointF
import android.os.Build
import android.os.IBinder
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.xxd.common.CommonFirst
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.extend.onClick
import com.xxd.common.service.CommonRemoteService
import com.xxd.myself.databinding.MyselfActivityServiceBinding

/**
 *    author : xxd
 *    date   : 2024/1/7
 *    desc   : 研究Service,未完成的类，未加入清单文件
 */
class ServiceActivity : BaseTitleActivity() {

    private lateinit var viewBinding: MyselfActivityServiceBinding

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = MyselfActivityServiceBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence {
        return "Service研究"
    }

    private var remoteService: CommonFirst? = null

    private val connect1 = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            remoteService = CommonFirst.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            remoteService = null
        }

    }

    override fun initView() {
        super.initView()

        viewBinding.tv0.onClick {
            Logger.d("开启其他进程service")
            val intent = Intent().apply {
                `package` = "com.xxd.service"  // 与服务端包名一致
                action = "com.xxd.startRemoteFirst"  // 与服务端 manifest 中 intent-filer定义的action一致
            }
            // 29 只能前台启动，否则报错
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // 启动后5秒报错，ForegroundServiceDidNotStartInTimeException，所以不推荐隐式意图开启Service
                startForegroundService(intent)
            } else {
                startService(intent)
            }

        }

        viewBinding.tv1.onClick {
            Logger.d("开启aidl")
            val intent = Intent().apply{
                component = ComponentName("com.xxd.service", CommonRemoteService::class.java.name)
            }
//            val intent = Intent(this,CommonRemoteService::class.java)
            this.bindService(intent, connect1, Context.BIND_AUTO_CREATE)
        }
        viewBinding.tv2.onClick {
            this.unbindService(connect1)
        }
        viewBinding.tv3.onClick {
//            Logger.d("获取 remoteInfo:${remoteService?.info}")

            val point = remoteService?.point(13, 4)
            Logger.d("commonPoint = $point")
        }
        viewBinding.tv4.onClick {
            val a = 12
            val b = 5
//            Logger.d("remoteAdd:$a + $b = ${remoteService?.add(a, b)}")
            val point = remoteService?.point(3, 4)
            Logger.d("point = $point")

        }
    }
}