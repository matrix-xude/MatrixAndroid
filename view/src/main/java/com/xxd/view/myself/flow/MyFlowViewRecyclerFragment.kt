package com.xxd.view.myself.flow

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.costom.decoration.CommonItemDecoration
import com.xxd.common.extend.binding
import com.xxd.common.extend.onClick
import com.xxd.view.databinding.ViewFragmentFirstViewGourpBinding
import com.xxd.view.databinding.ViewFragmentFirstViewGourpRecyclerBinding
import com.xxd.view.databinding.ViewItemFirstViewGourpRecyclerBinding

/**
 *    author : xxd
 *    date   : 2021/8/26
 *    desc   : 测试自定义的ViewGroup在RecyclerView中的表现
 */
class MyFlowViewRecyclerFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentFirstViewGourpRecyclerBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = ViewFragmentFirstViewGourpRecyclerBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(CommonItemDecoration().apply {
                boundary = 10
                interval = 10
            })
            adapter = object : BaseBindingQuickAdapter<String,ViewItemFirstViewGourpRecyclerBinding>(){
                override fun convert(holder: BaseBindingViewHolder<ViewItemFirstViewGourpRecyclerBinding>, item: String) {

                }

                override fun onItemViewHolderCreated(viewHolder: BaseBindingViewHolder<ViewItemFirstViewGourpRecyclerBinding>, viewType: Int) {
                    super.onItemViewHolderCreated(viewHolder, viewType)

                    viewHolder.binding.tv1.onClick {
                        val addTv = TextView(context).apply {
                            textSize = 20f
                            setTextColor(Color.BLUE)
                            text = "我是新加的   哈啊啊"
                            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        }
                        viewHolder.binding.flowView.addView(addTv)
                    }
                    viewHolder.binding.tv2.onClick {
                        viewHolder.binding.flowView.apply {
                            takeIf { isNotEmpty() }?.removeViewAt(childCount - 1)
                        }
                    }
                }
            }.apply {
                setList(listOf("1","1","1","1","1","1","1",))
            }
        }

    }
}