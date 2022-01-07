package com.xxd.myself

import android.view.View
import android.view.ViewGroup
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.logger.Logger
import com.tamsiree.rxkit.RxBarTool
import com.tamsiree.rxkit.RxDeviceTool
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.extend.onClick
import com.xxd.common.module.login.LoginOwner
import com.xxd.common.util.intent.IntentUtil
import com.xxd.common.util.toast.ToastUtil
import com.xxd.myself.databinding.MyselfActivityMainBinding
import com.xxd.myself.task.activity.FirstActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : BaseTitleActivity() {

    private lateinit var viewBinding: MyselfActivityMainBinding

    private val fragmentArray = listOf(BindingFragment(), BindingFragment2())
    private var currentFragmentIndex = 0

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = MyselfActivityMainBinding.inflate(layoutInflater,rootView,true)
    }

    override fun getTitleName(): CharSequence {
        return "随心所欲"
    }

    override fun initView() {
        super.initView()
        immersionBar {
            statusBarColor(R.color.common_white)
            fitsSystemWindows(true)
            statusBarDarkFont(true)
        }
        viewBinding.tv1.paint.isFakeBoldText = true
        viewBinding.tv2.paint.isFakeBoldText = true

        viewBinding.tv1.onClick {
            IntentUtil.startActivity<FirstActivity>(this)
        }


    }


    override fun initData() {
        super.initData()
        titleBinding.tvTitleName.text = "测试222"
        viewBinding.root

        /*viewBinding.tv1.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_contain, fragmentArray[currentFragmentIndex].apply {
                    currentFragmentIndex++
                    currentFragmentIndex %= 2
                })
                .commit()
        }*/

        viewBinding.tv2.onClick {
//            immersionBar {
//                hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
//            }
            ToastUtil.showToast("111111")
        }

//        LoginOwner.instance.change().setLoginStatus(true)

//        coroutines()
    }

    private fun coroutines(){
        // 用Handler进行了线程切换，最后打印
        GlobalScope.launch(Dispatchers.Main) {
            Logger.d("当前线程${Thread.currentThread().name} --》 成功1")
        }
        // 携程调度
        GlobalScope.launch(Dispatchers.IO) {
            Logger.d("当前线程${Thread.currentThread().name} --》 成功2")
        }
        // 当前线程直接执行携程，不进行handler切换，最先打印
        GlobalScope.launch(Dispatchers.Unconfined) {
            Logger.d("当前线程${Thread.currentThread().name} --》 成功3")
        }
        // 携程调度
        GlobalScope.launch(Dispatchers.Default) {
            Logger.d("当前线程${Thread.currentThread().name} --》 成功4")
        }
    }

}