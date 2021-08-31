package com.xxd.view.recycler.adapter.provider

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.view.R

/**
 *    author : xxd
 *    date   : 2021/8/16
 *    desc   :
 */
class ProviderInt : BaseItemProvider<ItemBase>() {

    override val itemViewType: Int
        get() = 2

    override val layoutId: Int
        get() = R.layout.common_simple_text

    override fun convert(helper: BaseViewHolder, item: ItemBase) {
        helper.setText(R.id.tv_name, "我是ID：${(item as ItemBean2).id}")
    }
}