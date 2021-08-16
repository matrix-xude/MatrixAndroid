package com.xxd.view.recycler.adapter.provider

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.databinding.CommonSimpleTextBinding
import com.xxd.view.R

/**
 *    author : xxd
 *    date   : 2021/8/16
 *    desc   :
 */
class ProviderString : BaseItemProvider<ItemBase>() {

    override val itemViewType: Int
        get() = 1

    override val layoutId: Int
        get() = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val vb = CommonSimpleTextBinding.inflate(LayoutInflater.from(context),null,false)
        return BaseBindingViewHolder(vb)
    }

    override fun convert(helper: BaseViewHolder, item: ItemBase) {
//        helper.setText(R.id.tv_name, "name : ${(item as ItemBean1).name}")
        helper as BaseBindingViewHolder<CommonSimpleTextBinding>
        helper.binding.tvName.text = (item as ItemBean1).name
    }
}