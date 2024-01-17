package com.xxd.myself.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Messenger
import android.view.ViewGroup
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.extend.onClick
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

    override fun initView() {
        super.initView()


        viewBinding.tv1.onClick {
            startService(Intent(this, SeekLifeService::class.java))
        }
        viewBinding.tv2.onClick {
            stopService(Intent(this, SeekLifeService::class.java))
        }
        viewBinding.tv3.onClick {
            bindService(Intent(),object : ServiceConnection{
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    TODO("Not yet implemented")
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    TODO("Not yet implemented")
                }

            },Context.BIND_AUTO_CREATE)
        }
        viewBinding.tv4.onClick {
            Messenger(Handler()).binder
        }
    }
}