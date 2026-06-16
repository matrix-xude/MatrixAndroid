package com.xxd.coroutine

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.R
import com.xxd.common.fast.SimpleListActivity
import com.xxd.common.util.intent.IntentUtil
import com.xxd.coroutine.old.CancelCoroutineActivity
import com.xxd.coroutine.old.ExceptionCoroutineActivity
import com.xxd.coroutine.lifecycle.LifecycleActivity
import com.xxd.coroutine.lifecycle.LifecycleSwitchActivity
import com.xxd.coroutine.vm.SearchVMActivity

class MainActivity : SimpleListActivity<String>() {

    private val itemArray =
        listOf("协程异常", "协程取消", "Lifecycle Activity", "Lifecycle Switch Activity", "ViewModel研究")

    override fun getDataList(): Collection<String> {
        return itemArray
    }

    override fun getItemLayoutResId(): Int {
        return R.layout.common_item_vertical_simple_text
    }

    override fun getTitleName(): CharSequence {
        return "协程"
    }

    override fun convertItem(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_name, item)
    }

    override fun initView() {
        super.initView()
        simpleAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> IntentUtil.startActivity<ExceptionCoroutineActivity>(this)
                1 -> IntentUtil.startActivity<CancelCoroutineActivity>(this)
                2 -> IntentUtil.startActivity<LifecycleActivity>(this)
                3 -> IntentUtil.startActivity<LifecycleSwitchActivity>(this)
                4 -> IntentUtil.startActivity<SearchVMActivity>(this) {
                    putExtra("name", "xxd")
                    putExtra("age", 18)
                }
            }
        }
    }

}