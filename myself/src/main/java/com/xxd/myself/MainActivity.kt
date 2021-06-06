package com.xxd.myself

import com.xxd.common.base.BaseTitleActivity
import com.xxd.myself.databinding.MyselfActivityMainBinding

class MainActivity : BaseTitleActivity() {

    private lateinit var myselfBinding: MyselfActivityMainBinding

    override fun getTitleName(): CharSequence? {
        return "随心所欲"
    }

    override fun viewBinding() {
        myselfBinding = MyselfActivityMainBinding.inflate(layoutInflater, super.rootView, true)
    }

    override fun initData() {
        super.initData()
        myselfBinding.tv1.text = "打分法"
        titlebinding.tvTitleName.text = "fadf"
    }

}