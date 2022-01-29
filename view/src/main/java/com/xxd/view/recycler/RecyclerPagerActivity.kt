package com.xxd.view.recycler

import androidx.fragment.app.Fragment
import com.xxd.common.fast.SimpleSwitchFragmentActivity
import com.xxd.view.recycler.adapter.DelegateMultiFragment
import com.xxd.view.recycler.adapter.MultiFragment
import com.xxd.view.recycler.adapter.provider.ProviderFragment
import com.xxd.view.recycler.fragment.PayloadFragment
import com.xxd.view.recycler.fragment.RecyclerFragment
import com.xxd.view.recycler.fragment.ReverseFragment
import com.xxd.view.recycler.fragment.SimpleFragment

/**
 *    author : xxd
 *    date   : 2021/8/16
 *    desc   :
 */
class RecyclerPagerActivity : SimpleSwitchFragmentActivity() {

    private val dataList = listOf("MultiFragment", "DelegateMultiFragment", "ProviderFragment", "RecyclerFragment", "PayloadFragment"
        , "SimpleFragment", "ReverseFragment")
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
            else -> throw RuntimeException("当前无内容")
        }
    }

    override fun getTitleName(): CharSequence {
        return "RecyclerView研究"
    }

}