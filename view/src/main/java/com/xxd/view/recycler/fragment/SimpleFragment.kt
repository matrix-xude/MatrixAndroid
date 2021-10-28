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
import com.xxd.common.databinding.CommonItemVerticalSimpleTextBinding
import com.xxd.common.extend.binding
import com.xxd.common.extend.onClick
import com.xxd.view.databinding.ViewFragmentRecyclerBinding

/**
 *    author : xxd
 *    date   : 2021/10/28
 *    desc   :
 */
class SimpleFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentRecyclerBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ViewFragmentRecyclerBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

        viewBinding.tv1.text = "删除1，3，5的条目"
        viewBinding.tv2.text = "删除5，3，1的条目"
        viewBinding.tv1.onClick {
            viewBinding.recyclerView.adapter?.notifyItemRemoved(0)
            viewBinding.recyclerView.adapter?.notifyItemRemoved(2)
            viewBinding.recyclerView.adapter?.notifyItemRemoved(4)
        }
        viewBinding.tv2.onClick {
            viewBinding.recyclerView.adapter?.notifyItemRemoved(4)
            viewBinding.recyclerView.adapter?.notifyItemRemoved(2)
            viewBinding.recyclerView.adapter?.notifyItemRemoved(0)
        }

        initRecycler()

    }

    private fun initRecycler() {
        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(CommonItemDecoration().apply {
                boundary = 10
                interval = 20
            })
            adapter = object : BaseBindingQuickAdapter<Int, CommonItemVerticalSimpleTextBinding>() {
                override fun convert(holder: BaseBindingViewHolder<CommonItemVerticalSimpleTextBinding>, item: Int) {
                    holder.binding.tvName.text = "条目$item"
                }

            }.apply {
                setList(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))
            }
        }
    }
}