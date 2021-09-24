package com.xxd.view.myself.nine

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tamsiree.rxkit.RxKeyboardTool
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.costom.decoration.CommonItemDecoration
import com.xxd.common.extend.binding
import com.xxd.common.util.log.LogUtil
import com.xxd.view.R
import com.xxd.view.databinding.ViewFragmentNineControlBinding
import com.xxd.view.databinding.ViewItemNineViewBinding

/**
 *    author : xxd
 *    date   : 2021/8/26
 *    desc   :
 */
class NineControlFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentNineControlBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = ViewFragmentNineControlBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

//        initNineControlView()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        val list = mutableListOf(1, 8, 4, 15, 0, 3, 1, 2, 7, 93, 5, 1, 23, 5, 1, 5, 8, 2, 1, 5, 1, 8, 4, 15, 0, 3, 1, 2, 7, 93, 5, 1, 23, 5, 1, 5, 8, 2, 1, 5)

        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(CommonItemDecoration().apply {
                interval = 10
                boundary = 10
            })
            adapter = object : BaseBindingQuickAdapter<Int, ViewItemNineViewBinding>() {

                override fun convert(holder: BaseBindingViewHolder<ViewItemNineViewBinding>, item: Int) {
                    LogUtil.d("创建九宫图adapter item=$item")
                    holder.binding.nineControlView.setAdapter(object : NineControlView.Adapter {
                        override fun getCount(): Int {
                            return item
                        }

                        override fun createView(position: Int): View {
                            return ImageView(context).apply {
                                setBackgroundColor(Color.RED)
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                Glide.with(context).load(R.drawable.view_bg_6).into(this)
                            }
                        }

                    })
                }
            }.apply {
                setList(list)
            }
        }
    }

//    private fun initNineControlView() {
//        viewBinding.nineControlView.setAdapter(object : NineControlView.Adapter {
//
//            override fun getCount(): Int {
//                return 8
//            }
//
//            override fun createView(position: Int): View {
//                return ImageView(context).apply {
//                    scaleType = ImageView.ScaleType.CENTER
//                    Glide.with(context).load(R.drawable.view_bg_2).into(this)
//                }
//            }
//        })
//    }
}