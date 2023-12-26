package com.xxd.myself

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.gyf.immersionbar.ktx.immersionBar
import com.xxd.common.fast.SimpleListActivity
import com.xxd.common.util.intent.IntentUtil
import com.xxd.myself.dp.DpActivity

class MainActivity : SimpleListActivity<String>() {

    private val mDataList = listOf("Px Dp 与 屏幕适配")

    override fun initView() {
        super.initView()
        immersionBar {
            statusBarColor(R.color.common_transparent)
            fitsSystemWindows(false)
            statusBarDarkFont(true)
        }

        initClickListener()
    }

    private fun initClickListener() {
        simpleAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> IntentUtil.startActivity<DpActivity>(this)
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
        return "不好分类的功能集合"
    }


}