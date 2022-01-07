package com.xxd.thread

import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.fast.SimpleListActivity
import com.xxd.common.util.intent.IntentUtil
import com.xxd.myself.task.activity.SecondActivity
import com.xxd.thread.basic.ThreadBasicActivity
import com.xxd.thread.encode.EncodeActivity
import com.xxd.thread.rxjava.RxJavaActivity

@Route(path = "/thread/activity/main")
class MainActivity : SimpleListActivity<String>() {

    private val dataList = listOf("线程基础", "序列化、Json", "RxJava", "星辰大海")

    override fun getTitleName(): CharSequence {
        return "线程总汇"
    }

    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getItemLayoutResId(): Int {
        return R.layout.common_item_vertical_simple_text
    }

    override fun convertItem(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_name, item)
    }

    override fun initView() {
        super.initView()
        initListener()
    }

    private fun initListener() {
        simpleAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> IntentUtil.startActivity<ThreadBasicActivity>(this)
                1 -> IntentUtil.startActivity<EncodeActivity>(this)
                2 -> IntentUtil.startActivity<RxJavaActivity>(this)
                3 -> {
                    IntentUtil.startActivity<SecondActivity>(this){
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                }

            }
        }
    }

}
