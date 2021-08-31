package com.xxd.view.recycler.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.decoration.CommonItemDecoration
import com.xxd.common.databinding.CommonSimpleRecyclerViewBinding
import com.xxd.common.extend.binding

/**
 *    author : xxd
 *    date   : 2021/8/16
 *    desc   :
 */
class DelegateMultiFragment : BaseFragment() {

    private var viewBinding by binding<CommonSimpleRecyclerViewBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = CommonSimpleRecyclerViewBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(CommonItemDecoration().apply {
                boundary = 10
                interval = 20
            })
            adapter = DelegateMultiAdapter().apply {
                setList(fakeData())
            }
        }

    }

    private fun fakeData(): MutableList<String> {
       return mutableListOf<String>().apply {
            repeat(20) {
                val random = (1..3).random()
                add("哈哈$random")
            }
        }
    }
}