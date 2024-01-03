package com.xxd.view.recycler

import androidx.fragment.app.Fragment
import com.xxd.common.extend.onClick
import com.xxd.common.fast.SimpleSwitchFragmentActivity
import com.xxd.common.util.intent.IntentUtil
import com.xxd.view.recycler.adapter.DelegateMultiFragment
import com.xxd.view.recycler.adapter.MultiFragment
import com.xxd.view.recycler.adapter.provider.ProviderFragment
import com.xxd.view.recycler.diff.RecyclerDiffActivity
import com.xxd.view.recycler.fragment.*

/**
 *    author : xxd
 *    date   : 2021/8/16
 *    desc   :
 */
class RecyclerPagerActivity : SimpleSwitchFragmentActivity() {

    private val dataList = listOf(
        "MultiFragment", "DelegateMultiFragment", "ProviderFragment", "RecyclerFragment", "PayloadFragment", "SimpleFragment",
        "ReverseFragment", "WrapFragment", "跳转到RecyclerDiffActivity"
    )

    override fun initView() {
        super.initView()
        // 增加一个点击标题跳转到RecyclerDiffActivity的功能
        titleBinding.tvTitleName.onClick {
            IntentUtil.startActivity<RecyclerDiffActivity>(this)
        }
    }

    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getPositionFragment(position: Int): Fragment {
        return when (position) {
            0 -> MultiFragment()
            1 -> DelegateMultiFragment()
            2 -> ProviderFragment()
            3 -> RecyclerFragment()
            4 -> PayloadFragment()
            5 -> SimpleFragment()
            6 -> ReverseFragment()
            7 -> WrapFragment()
            else -> throw RuntimeException("当前无内容")
        }
    }

    override fun getTitleName(): CharSequence {
        return "RecyclerView研究"
    }

}