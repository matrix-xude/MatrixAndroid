package com.xxd.view.recycler.adapter

import android.graphics.Color
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.view.R

/**
 *    author : xxd
 *    date   : 2021/8/16
 *    desc   :
 */
class MultiAdapter :
    BaseMultiItemQuickAdapter<ItemBase, BaseViewHolder>() {

    init {
        addItemType(1, R.layout.common_simple_text)
        addItemType(2, R.layout.common_simple_text)
        addItemType(3, R.layout.common_simple_text)
    }

    override fun convert(holder: BaseViewHolder, item: ItemBase) {
        when (holder.itemViewType) {
            1 -> {
                val textView = holder.getView<TextView>(R.id.tv_name)
                textView.setBackgroundColor(Color.RED)
                textView.layoutParams = textView.layoutParams.apply {
                    height = 100
                }
            }
            2 -> {
                val textView = holder.getView<TextView>(R.id.tv_name)
                textView.setBackgroundColor(Color.YELLOW)
                textView.layoutParams = textView.layoutParams.apply {
                    height = 200
                }
            }
            3 -> {
                val textView = holder.getView<TextView>(R.id.tv_name)
                textView.setBackgroundColor(Color.BLUE)
                textView.layoutParams.apply {
                    height = 150
                }
            }
        }
    }
}
