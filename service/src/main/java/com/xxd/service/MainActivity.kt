package com.xxd.service

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.gyf.immersionbar.ktx.immersionBar
import com.xxd.common.fast.SimpleListActivity


class MainActivity : SimpleListActivity<String>() {

    private val mDataList = listOf("开启Service","AIDL")

    override fun initView() {
        super.initView()
        immersionBar {
            statusBarColor(R.color.common_transparent)
            fitsSystemWindows(true)
            statusBarDarkFont(true)
        }

        initClickListener()
    }

    private fun initClickListener() {
        simpleAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> {}
            }
        }
    }

    override fun getDataList(): Collection<String> {
        return mDataList
    }

    override fun getItemLayoutResId(): Int {
        return R.layout.common_item_vertical_simple_text
    }

    override fun convertItem(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_name, item)
    }

    override fun getTitleName(): CharSequence {
        return "服务相关"
    }


}