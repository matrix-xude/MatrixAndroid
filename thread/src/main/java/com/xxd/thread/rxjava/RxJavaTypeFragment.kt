package com.xxd.thread.rxjava

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.costom.decoration.CommonItemDecoration
import com.xxd.common.databinding.CommonItemSimpleTextBinding
import com.xxd.thread.databinding.ThreadFragmentRxjavaTypeBinding

/**
 *    author : xxd
 *    date   : 2023/12/18
 *    desc   :
 */
class RxJavaTypeFragment : BaseFragment() {

    private lateinit var viewBinding: ThreadFragmentRxjavaTypeBinding
    private lateinit var adapter: BaseBindingQuickAdapter<String, CommonItemSimpleTextBinding>
    private val list = listOf("Single", "Completable", "Maybe", "Observable", "Flowable", "Single2", "Completable2", "Maybe2", "Observable2", "Flowable2")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ThreadFragmentRxjavaTypeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        initRecyclerView()
    }

    override fun initDataLazy() {
        adapter.setList(list)
    }

    private fun initRecyclerView() {
        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this@RxJavaTypeFragment.adapter = object : BaseBindingQuickAdapter<String, CommonItemSimpleTextBinding>() {
                override fun convert(holder: BaseBindingViewHolder<CommonItemSimpleTextBinding>, item: String) {
                    holder.binding.tvName.text = item
                }
            }
            adapter = this@RxJavaTypeFragment.adapter

            addItemDecoration(CommonItemDecoration().apply {
                boundary = 15
                interval = 20
            })
        }
    }


}