package com.xxd.view

import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.fast.SimpleListActivity
import com.xxd.common.util.intent.IntentUtil
import com.xxd.view.material.MaterialDesignActivity
import com.xxd.view.recycler.RecyclerDiffActivity
import com.xxd.view.systemWidget.SystemWidgetActivity

@Route(path = "/view/activity/main")
class MainActivity : SimpleListActivity<String>() {

    private val mDataList = mutableListOf(
        "lifecycle",
        "RecyclerView",
        "系统的view",
        "meter design",
        "自定义的view",
        "终章"
    )

    override fun initView() {
        super.initView()
        val extras = intent.extras
        val stringExtra = intent.getStringExtra("key")
        initListener()
    }

    private fun initListener() {
        simpleAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                1 -> IntentUtil.startActivity<RecyclerDiffActivity>(this)
                2 -> IntentUtil.startActivity<SystemWidgetActivity>(this)
                3 -> IntentUtil.startActivity<MaterialDesignActivity>(this)
            }
        }
    }

    override fun getTitleName(): CharSequence {
        return "View集合"
    }

    override fun getDataList(): Collection<String> {
        return mDataList
    }

    override fun getItemLayoutResId(): Int {
        return R.layout.common_item_vertical_simple_text
    }

    override fun convertItem(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_Name, item)
    }

}
