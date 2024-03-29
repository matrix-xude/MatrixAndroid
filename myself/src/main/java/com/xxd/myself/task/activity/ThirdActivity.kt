package com.xxd.myself.task.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.ViewGroup
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.extend.onClick
import com.xxd.common.util.intent.IntentUtil
import com.xxd.myself.databinding.MyselfActivityTaskThirdBinding

/**
 *    author : xxd
 *    date   : 2022/1/7
 *    desc   :
 */
class ThirdActivity : BaseTitleActivity() {

    private lateinit var viewBinding: MyselfActivityTaskThirdBinding

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = MyselfActivityTaskThirdBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence {
        return "第三个Activity"
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()

        viewBinding.tv2.text = "taskId=$taskId"
        viewBinding.tv1.onClick {
            IntentUtil.startActivity<SecondActivity>(this){
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
    }
}