package com.xxd.view

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.base.BaseActivity
import com.xxd.view.recycler.CommonItemDecoration
import com.xxd.view.recycler.RecyclerActivity
import com.xxd.view.systemWidget.SystemWidgetActivity
import kotlinx.android.synthetic.main.view_activity_main.*

@Route(path = "/view/activity/main")
class MainActivity : BaseActivity() {

    private val mDataList = mutableListOf(
        "lifecycle",
        "RecyclerView",
        "系统的view",
        "自定义的view",
        "终章"
    )
    private lateinit var mAdapter: BaseQuickAdapter<String, BaseViewHolder>

    override fun getLayoutId(): Int {
        return R.layout.view_activity_main
    }

    override fun initView() {
        super.initView()
        initRecyclerView()
        initListener()
    }

    override fun initData() {
        super.initData()
        mAdapter.setNewInstance(mDataList)
    }

    private fun initListener() {
        mAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                1 -> toActivity(RecyclerActivity::class.java)
                2 -> toActivity(SystemWidgetActivity::class.java)
            }
        }
    }

    private fun toActivity(clazz: Class<out Any>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }


    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvMain.layoutManager = manager
        rvMain.addItemDecoration(CommonItemDecoration().apply {
            interval = 20
            headOffset = 30
            tailOffset = 30
            boundary = 15
        })
        mAdapter =
            object :
                BaseQuickAdapter<String, BaseViewHolder>(R.layout.common_item_vertical_simple_text) {
                override fun convert(holder: BaseViewHolder, item: String) {
                    holder.setText(R.id.tvName, item)
                }
            }
        rvMain.adapter = mAdapter
    }

}
