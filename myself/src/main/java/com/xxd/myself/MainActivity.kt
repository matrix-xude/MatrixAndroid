package com.xxd.myself

import android.view.View
import com.xxd.common.base.BaseTitleActivity
import com.xxd.myself.databinding.MyselfActivityMainBinding

class MainActivity : BaseTitleActivity() {

    private lateinit var myselfBinding: MyselfActivityMainBinding

    override fun getTitleName(): CharSequence? {
        return "随心所欲"
    }

    override fun getContentView(): View {
        myselfBinding = MyselfActivityMainBinding.inflate(layoutInflater)
        return myselfBinding.root
    }

    override fun initData() {
        super.initData()
        myselfBinding.tv1.text = "打分法"
        titleBinding.tvTitleName.text = "fadf"
    }

}