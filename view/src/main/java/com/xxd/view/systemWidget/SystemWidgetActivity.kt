package com.xxd.view.systemWidget

import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.base.BaseActivity
import com.xxd.common.tool.CommonItemDecoration
import com.xxd.view.R
import kotlinx.android.synthetic.main.view_activity_system_widget.*

/**
 *    author : xxd
 *    date   : 2020/8/20
 *    desc   :
 */
class SystemWidgetActivity : BaseActivity() {

    private lateinit var adapter: BaseQuickAdapter<String, BaseViewHolder>
    private val dataList = listOf("ViewFlipper", "", "", "", "", "", "")



    override fun initView() {
        super.initView()
        initRecyclerView()
        initListener()
    }

    override fun initData() {
        super.initData()
        adapter.setList(dataList)
    }

    private fun initListener() {
        adapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flContent, ViewFlipperFragment()).commit()
                }
                1 -> {
                    ViewFlipperFragment()
                }
            }
        }

    }


    private fun initRecyclerView() {
        rvTop.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTop.addItemDecoration(CommonItemDecoration().apply {
            interval = 15
            boundary = 15
        })
        adapter = object :
            BaseQuickAdapter<String, BaseViewHolder>(R.layout.common_item_horizontal_simple_text) {
            override fun convert(holder: BaseViewHolder, item: String) {
                holder.setText(R.id.tvName, item)
            }
        }
        rvTop.adapter = adapter
    }

}