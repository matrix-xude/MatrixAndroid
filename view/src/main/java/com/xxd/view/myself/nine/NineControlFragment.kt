package com.xxd.view.myself.nine

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.costom.decoration.CommonItemDecoration
import com.xxd.common.extend.binding
import com.xxd.common.util.log.LogUtil
import com.xxd.view.R
import com.xxd.view.databinding.ViewFragmentNineControlBinding
import com.xxd.view.databinding.ViewItemNineViewBinding
import java.util.logging.Logger

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

//                    if (holder.binding.nineControlView.getAdapter() == null) {
                        LogUtil.d("创建九宫图adapter item=$item")
                        holder.binding.nineControlView.setAdapter(object : NineControlView.Adapter {
                            override fun getCount(): Int {
//                                val p = holder.adapterPosition - headerLayoutCount
//                                return list[p]
                                return item
                            }

                            override fun onCreateViewHolder(): NineControlView.ViewHolder {
                                val imageView = ImageView(context).apply {
                                    scaleType = ImageView.ScaleType.CENTER
                                    setBackgroundColor(context.resources.getColor(R.color.common_aliceblue))
                                }
                                return object : NineControlView.ViewHolder(imageView) {}
                            }

                            override fun onBindingViewHolder(viewHolder: NineControlView.ViewHolder, position: Int) {
                                LogUtil.d("onBindingViewHolder , position=$position")
                                viewHolder.itemView as ImageView
                                Glide.with(context).load(R.drawable.view_bg_2).into(viewHolder.itemView)
//                                viewHolder.itemView.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.view_bg_2))
                            }
                        })
//                    } else {
//                        LogUtil.d("九宫图adapter刷新 item=$item")
//                        holder.binding.nineControlView.refreshAdapter()
//                    }
                }
            }.apply {
                setList(list)
            }
        }
    }

    private fun initNineControlView() {
        viewBinding.nineControlView.setAdapter(object : NineControlView.Adapter {

            override fun getCount(): Int {
                return 8
            }

            override fun onCreateViewHolder(): NineControlView.ViewHolder {
                val imageView = ImageView(context).apply {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
                return object : NineControlView.ViewHolder(imageView) {}
            }

            override fun onBindingViewHolder(viewHolder: NineControlView.ViewHolder, position: Int) {
                viewHolder.itemView as ImageView
                viewHolder.itemView.setImageBitmap(BitmapFactory.decodeResource(context!!.resources, R.drawable.view_bg_2))
            }
        })
    }
}