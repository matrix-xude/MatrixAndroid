package com.xxd.view

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.base.BaseActivity
import com.xxd.common.util.LogUtil
import com.xxd.view.recycler.CommonItemDecoration
import kotlinx.android.synthetic.main.view_activity_main.*

@Route(path = "/view/activity/main")
class MainActivity : BaseActivity() {

    private val mDataList = mutableListOf("生命周期观察", "er\r\nerer", "333", "", "", "", "", "", "", "", "", "", "", "", "", "终章")
    private lateinit var mAdapter: BaseQuickAdapter<String, BaseViewHolder>

    override fun getLayoutId(): Int {
        return R.layout.view_activity_main
    }

    override fun initView() {
        super.initView()
        initRecyclerView()
    }


    override fun initData() {
        super.initData()
        mAdapter.setNewInstance(mDataList)

        LogUtil().test()
    }

    private fun initRecyclerView() {
//        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val manager = GridLayoutManager(this,5,LinearLayoutManager.HORIZONTAL,false)
        rvMain.layoutManager = manager
        rvMain.addItemDecoration(CommonItemDecoration().apply {
            interval = 5
            headOffset = 55
            tailOffset = 44
            boundaryEnd = 8
            boundaryStart = 40
        })
        mAdapter =
            object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.common_item_simple_text) {
                override fun convert(holder: BaseViewHolder, item: String) {
                    holder.setText(R.id.tvName, item)
                }

            }
        rvMain.adapter = mAdapter

    }

}
