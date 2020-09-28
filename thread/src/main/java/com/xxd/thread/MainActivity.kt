package com.xxd.thread

import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.fast.SimpleListActivity
import com.xxd.common.util.IntentUtil
import com.xxd.common.util.log.LogUtil
import com.xxd.thread.basic.ThreadBasicActivity
import com.xxd.thread.encode.EncodeActivity
import com.xxd.thread.encode.ParcelableJava

@Route(path = "/thread/activity/main")
class MainActivity : SimpleListActivity<String>() {

    private val dataList = listOf("线程基础", "线程切换", "RxJava", "测试页面")

    override fun getTitleName(): CharSequence? {
        return "线程总汇"
    }

    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getItemLayoutResId(): Int {
        return R.layout.common_item_vertical_simple_text
    }

    override fun convertItem(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tvName, item)
    }

    override fun initView() {
        super.initView()
        initListener()
    }

    private fun initListener() {
        simpleAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> IntentUtil.startActivity(this, ThreadBasicActivity::class.java)
                1 -> IntentUtil.startActivity(this, EncodeActivity::class.java)
            }
        }
    }

}
