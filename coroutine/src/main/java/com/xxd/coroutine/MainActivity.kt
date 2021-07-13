package com.xxd.coroutine

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.R
import com.xxd.common.fast.SimpleListActivity
import com.xxd.common.util.intent.IntentUtil
import com.xxd.coroutine.cancel.CancelCoroutineActivity
import com.xxd.coroutine.exception.ExceptionCoroutineActivity

class MainActivity : SimpleListActivity<String>() {

    private val itemArray = listOf("携程异常","携程取消")

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
        simpleAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> IntentUtil.startActivity<ExceptionCoroutineActivity>(this)
                1 -> IntentUtil.startActivity<CancelCoroutineActivity>(this)
            }
        }
    }

}