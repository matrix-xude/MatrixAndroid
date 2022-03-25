package com.xxd.view.recycler.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.Glide
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.costom.decoration.CommonItemDecoration
import com.xxd.common.databinding.CommonItemVerticalSimpleTextBinding
import com.xxd.common.extend.*
import com.xxd.common.util.log.LogUtil
import com.xxd.view.R
import com.xxd.view.databinding.ViewFragmentRecyclerPayloadBinding
import com.xxd.view.databinding.ViewFragmentRecyclerWrapBinding
import com.xxd.view.databinding.ViewItemNineViewSpecialBinding
import com.xxd.view.myself.nine.NineControlSpecialView

/**
 *    author : xxd
 *    date   : 2021/10/16
 *    desc   : RecyclerView测试包裹布局的加载效果
 */
class WrapFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentRecyclerWrapBinding>()
    private lateinit var adapter: BaseBindingQuickAdapter<Int, CommonItemVerticalSimpleTextBinding>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ViewFragmentRecyclerWrapBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

        initRecycler()
        initClick()
    }

    private fun initClick() {
        viewBinding.tv1.onClick {
            val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19)
            adapter.setList(list)
            viewBinding.recyclerView.post {
                LogUtil.d("tvTitle.top = ${viewBinding.tvTitle.top}")
                if (viewBinding.tvTitle.top == 0) { // 表示超过了最高高度
                    val layoutParams = viewBinding.recyclerView.layoutParams as LinearLayout.LayoutParams
                    layoutParams.height = 0
                    layoutParams.weight = 1f
                    viewBinding.recyclerView.layoutParams = layoutParams

                    val layoutParams2 = viewBinding.v1.layoutParams as LinearLayout.LayoutParams
                    layoutParams2.weight = 0f
                    viewBinding.v1.layoutParams = layoutParams2
                }
            }
        }
        viewBinding.tv2.onClick {
            val list = listOf(1, 2, 3)
            adapter.setList(list)
            viewBinding.recyclerView.post {
                LogUtil.d("tvTitle.top = ${viewBinding.tvTitle.top}")
                if (viewBinding.tvTitle.top == 0) { // 表示超过了最高高度
                    val layoutParams = viewBinding.recyclerView.layoutParams as LinearLayout.LayoutParams
                    layoutParams.height = 0
                    layoutParams.weight = 1f
                    viewBinding.recyclerView.layoutParams = layoutParams

                    val layoutParams2 = viewBinding.v1.layoutParams as LinearLayout.LayoutParams
                    layoutParams2.weight = 0f
                    viewBinding.v1.layoutParams = layoutParams2
                }
            }
        }
    }

    override fun initDataLazy() {
        super.initDataLazy()

//        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19)
        val list = listOf(1, 2, 3, 4)
        adapter.setList(list)
        viewBinding.recyclerView.post {
            LogUtil.d("tvTitle.top = ${viewBinding.tvTitle.top}")
            if (viewBinding.tvTitle.top == 0) { // 表示超过了最高高度
                val layoutParams = viewBinding.recyclerView.layoutParams as LinearLayout.LayoutParams
                layoutParams.height = 0
                layoutParams.weight = 1f
                viewBinding.recyclerView.layoutParams = layoutParams

                val layoutParams2 = viewBinding.v1.layoutParams as LinearLayout.LayoutParams
                layoutParams2.weight = 0f
                viewBinding.v1.layoutParams = layoutParams2
            }
        }


    }


    private fun initRecycler() {

        adapter = object : BaseBindingQuickAdapter<Int, CommonItemVerticalSimpleTextBinding>() {

            @SuppressLint("SetTextI18n")
            override fun convert(holder: BaseBindingViewHolder<CommonItemVerticalSimpleTextBinding>, item: Int) {
                val position = getPositionFromHolder(holder)
                LogUtil.d("当前的条目被加载了 : $position")
                holder.binding.tvName.text = "条目$item"
            }
        }

        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(CommonItemDecoration().apply {
                boundary = 10
                interval = 20
            })
            adapter = this@WrapFragment.adapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

}