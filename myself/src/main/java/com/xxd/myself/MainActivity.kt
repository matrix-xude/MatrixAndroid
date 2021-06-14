package com.xxd.myself

import android.view.View
import android.widget.TextView
import com.xxd.common.base.BaseTitleActivity
import com.xxd.myself.databinding.MyselfActivityMainBinding
import com.xxd.myself.databinding.MyselfTestViewGroupBinding

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
        titleBinding.tvTitleName.text = "测试"

        myselfBinding.tv1.setOnClickListener {  }




//        val view = layoutInflater.inflate(R.layout.myself_test_view_group, LinearLayout(this), false)
//        myselfBinding.cl1.addView(view)

//        layoutInflater.inflate(R.layout.myself_test_view_group, myselfBinding.cl2, true)
//
//
//        val llOuter : MyselfTestViewGroupBinding = myselfBinding.llOuter
//        llOuter.tvInner1.text = "33333333333333"

    }

}