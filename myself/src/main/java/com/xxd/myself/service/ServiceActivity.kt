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
import com.xxd.service.MyFirstDo

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
    private var remoteService2: MyFirstDo? = null

    private val connect1 = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Logger.d("onServiceConnected name=$name service=$service")
            remoteService = CommonFirst.Stub.asInterface(service)
            Logger.d("remoteService = $remoteService")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Logger.d("onServiceDisconnected name=$name")
            remoteService = null
        }
    }

    private val connect11 = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Logger.d("11 onServiceConnected name=$name service=$service")
            remoteService = CommonFirst.Stub.asInterface(service)
            Logger.d("11 remoteService = $remoteService")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Logger.d("11 onServiceDisconnected name=$name")
            remoteService = null
        }
    }

    private val connect12 = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Logger.d("12 onServiceConnected name=$name service=$service")
            remoteService = CommonFirst.Stub.asInterface(service)
            Logger.d("12 remoteService = $remoteService")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Logger.d("12 onServiceDisconnected name=$name")
            remoteService = null
        }
    }

    private val connect2 = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Logger.d("onServiceConnected name=$name service=$service")
            remoteService2 = MyFirstDo.Stub.asInterface(service)
            Logger.d("remoteService = $remoteService")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Logger.d("onServiceDisconnected name=$name")
            remoteService2 = null
        }
    }

    override fun initView() {
        super.initView()

        viewBinding.tv0.onClick {
            Logger.d("开启其他进程service")
//            val intent = Intent().apply {
//                `package` = "com.xxd.service"  // 与服务端包名一致
//                action = "com.xxd.startRemoteFirst"  // 与服务端 manifest 中 intent-filer定义的action一致
//            }
//            // 29 只能前台启动，否则报错
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                // 启动后5秒报错，ForegroundServiceDidNotStartInTimeException，所以不推荐隐式意图开启Service
//                startForegroundService(intent)
//            } else {
//                startService(intent)
//            }

            // 使用包名开启服务无效
            val intent = Intent().apply{
                component = ComponentName("com.xxd.service", "com.xxd.service.service.FirstService")
            }
            startService(intent)

        }

        viewBinding.tv1.onClick {
            Logger.d("开启aidl")
            val intent = Intent().apply{
//                component = ComponentName("com.xxd.service", CommonRemoteService::class.java.name)
                // 使用包名开启服务无效
                component = ComponentName("com.xxd.service", "com.xxd.common.service.CommonRemoteService")
            }
//            val intent = Intent(this,CommonRemoteService::class.java)
            val bindService = this.bindService(intent, connect1, Context.BIND_AUTO_CREATE)
            val bindService1 = this.bindService(intent, connect11, Context.BIND_AUTO_CREATE)
            val bindService2 = this.bindService(intent, connect12, Context.BIND_AUTO_CREATE)
            Logger.d("bindService=$bindService bindService1=$bindService1 bindService2=$bindService2 ")
        }
        viewBinding.tv2.onClick {
            this.unbindService(connect1)
        }
        viewBinding.tv3.onClick {
            Logger.d("获取 remoteInfo:${remoteService?.info}")
        }
        viewBinding.tv4.onClick {
            val a = 12
            val b = 5
            Logger.d("remoteAdd:$a + $b = ${remoteService?.add(a, b)}")
        }
        viewBinding.tv5.onClick {
            val point = remoteService?.point(13, 4)
            Logger.d("commonPoint = $point")
        }
        viewBinding.tv6.onClick {
            val point = remoteService?.point(3, 4)
            Logger.d("point = $point")
        }

        viewBinding.tv11.onClick {
            Logger.d("开启aidl ....")
            val intent = Intent().apply{
                component = ComponentName("com.xxd.service", "com.xxd.service.service.RemoteService")
            }
            // 本地引用不到的包名，使用包名开启Service无效
            this.bindService(intent, connect2, Context.BIND_AUTO_CREATE)
        }
        viewBinding.tv12.onClick {
            this.unbindService(connect2)
        }
        viewBinding.tv13.onClick {
            Logger.d("获取 remoteInfo:${remoteService2?.info}")
        }
        viewBinding.tv14.onClick {
            val a = 12
            val b = 5
            Logger.d("remoteAdd:$a + $b = ${remoteService2?.add(a, b)}")
        }
        viewBinding.tv15.onClick {
        }
        viewBinding.tv16.onClick {
        }

    }
}