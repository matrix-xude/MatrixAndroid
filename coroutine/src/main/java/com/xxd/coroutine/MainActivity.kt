package com.xxd.coroutine

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.R
import com.xxd.common.fast.SimpleListActivity

class MainActivity : SimpleListActivity<String>() {

    private val itemArray = listOf("携程异常")

    override fun getDataList(): Collection<String> {
        return itemArray
    }

    override fun getItemLayoutResId(): Int {
        return R.layout.common_item_vertical_simple_text
    }

    override fun getTitleName(): CharSequence {
        return "携程"
    }

    override fun convertItem(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tvName, item)
    }

    override fun initView() {
        super.initView()
        simpleAdapter.setOnItemChildClickListener { _, _, position ->
            when(position){
//                0 -> IntentUtil.startActivity(this,)
            }
        }
    }

}