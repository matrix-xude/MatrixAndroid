package com.xxd.myself

import com.xxd.common.base.BaseTitleActivity
import com.xxd.common.extend.binding
import com.xxd.myself.databinding.MyselfActivityMainBinding

class MainActivity : BaseTitleActivity() {

    private val myselfBinding: MyselfActivityMainBinding by binding(true)

    private val fragmentArray = listOf(BindingFragment(), BindingFragment2())
    private var currentFragmentIndex = 0

    override fun getTitleName(): CharSequence {
        return "随心所欲"
    }

    override fun initData() {
        super.initData()
        titleBinding.tvTitleName.text = "测试222"
        myselfBinding.root

        myselfBinding.tv1.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_contain, fragmentArray[currentFragmentIndex].apply {
                    currentFragmentIndex++
                    currentFragmentIndex %= 2
                })
                .commit()
        }
    }

}