package com.xxd.view.recycler.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.costom.decoration.CommonItemDecoration
import com.xxd.common.extend.binding
import com.xxd.view.databinding.ViewFragmentRecyclerBinding
import com.xxd.view.databinding.ViewItemBanner2Binding
import com.xxd.view.databinding.ViewItemFirstViewGourpRecyclerBinding
import com.xxd.view.recycler.adapter.MultiAdapter

/**
 *    author : xxd
 *    date   : 2021/8/26
 *    desc   :
 */
class RecyclerFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentRecyclerBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ViewFragmentRecyclerBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

        initRecycler()

    }

    private fun initRecycler() {
        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
//            addItemDecoration(CommonItemDecoration().apply {
//                boundary = 10
//                interval = 20
//            })
            adapter = object :BaseBindingQuickAdapter<Int,ViewItemFirstViewGourpRecyclerBinding>(){
                override fun convert(holder: BaseBindingViewHolder<ViewItemFirstViewGourpRecyclerBinding>, item: Int) {

                }

            }.apply {
                setList(listOf(1,3,4,5,6,4))
            }
        }
    }
}