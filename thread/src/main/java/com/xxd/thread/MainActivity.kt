package com.xxd.thread

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.base.BaseTitleActivity
import com.xxd.common.tool.CommonItemDecoration

@Route(path = "/thread/activity/main")
class MainActivity : BaseTitleActivity() {

    lateinit var adapter: BaseQuickAdapter<String, BaseViewHolder>
    private lateinit var recyclerView: RecyclerView
    private val dataList = listOf("线程基础","线程切换","RxJava")

    override fun getTitleName(): CharSequence? {
        return "线程总汇"
    }

    override fun getLayoutId(): Int {
        return R.layout.common_simple_recycler_view
    }

    override fun initView() {
        super.initView()
        recyclerView = rootView.findViewById(R.id.recyclerView)
        initRecyclerView()
    }

    override fun initData() {
        super.initData()
        adapter.setList(dataList)
    }

    private fun initRecyclerView() {

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(CommonItemDecoration().apply {
            boundary = 20
            interval = 15
        })
        adapter = object :
            BaseQuickAdapter<String, BaseViewHolder>(R.layout.common_item_vertical_simple_text) {
            override fun convert(holder: BaseViewHolder, item: String) {
                holder.setText(R.id.tvName, item)
            }

        }
        recyclerView.adapter = adapter
    }
}
