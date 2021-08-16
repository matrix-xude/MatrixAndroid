package com.xxd.view.recycler.adapter.provider

import com.chad.library.adapter.base.BaseProviderMultiAdapter

/**
 *    author : xxd
 *    date   : 2021/8/16
 *    desc   :
 */
class AdapterProvider : BaseProviderMultiAdapter<ItemBase>() {

    init {
        addItemProvider(ProviderString())
        addItemProvider(ProviderInt())
        addItemProvider(ProviderDouble())
    }

    override fun getItemType(data: List<ItemBase>, position: Int): Int {
        return data[position].type
    }
}