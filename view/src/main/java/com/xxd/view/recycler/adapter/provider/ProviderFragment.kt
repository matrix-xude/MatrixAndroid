package com.xxd.view.recycler.adapter.provider

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
class ProviderFragment : BaseFragment() {

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
            adapter = AdapterProvider().apply {
                setList(fakeData())
            }
        }

    }

    private fun fakeData(): MutableList<ItemBase> {
       return mutableListOf<ItemBase>().apply {
            add(ItemBean1(1,"哈哈哈"))
           add(ItemBean2(2,22))
           add(ItemBean2(2,11))
           add(ItemBean1(1,"哈哈哈"))
           add(ItemBean3(3,1.35))
           add(ItemBean1(1,"哈哈哈"))
           add(ItemBean2(2,75))
           add(ItemBean3(3,-0.09))
           add(ItemBean2(2,322))
           add(ItemBean2(2,5))
           add(ItemBean3(3,32.35))
           add(ItemBean1(1,"哈哈哈"))
           add(ItemBean3(3,66.66))
        }
    }
}