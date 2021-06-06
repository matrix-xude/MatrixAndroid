package com.xxd.common.fast

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.R
import com.xxd.common.base.BaseTitleActivity
import com.xxd.common.databinding.CommonActivitySwitchFragmentBinding
import com.xxd.common.tool.CommonItemDecoration

/**
 *    author : xxd
 *    date   : 2020/8/28
 *    desc   : 一个简单的切换Fragment的Activity，通过滑动上面的RecyclerView切换下面的Fragment
 *    简单的替换Fragment，不做缓存
 */
abstract class SimpleSwitchFragmentActivity : BaseTitleActivity() {

    /**
     * 顶部横滑recyclerView的adapter
     */
    private lateinit var simpleAdapter: BaseQuickAdapter<String, BaseViewHolder>

    protected lateinit var simpleSwitchBinding: CommonActivitySwitchFragmentBinding

    override fun viewBinding() {
        simpleSwitchBinding =
            CommonActivitySwitchFragmentBinding.inflate(layoutInflater, super.rootView, true)
    }

    override fun getRoot(): ViewGroup {
        return simpleSwitchBinding.root
    }

    /**
     * 获取数据
     */
    abstract fun getDataList(): Collection<String>

    abstract fun getPositionFragment(position: Int): Fragment


    override fun initView() {
        super.initView()
        initRecyclerView()
        initListener()
    }

    override fun initData() {
        super.initData()
        simpleAdapter.setList(getDataList())
    }

    private fun initListener() {
        simpleAdapter.setOnItemClickListener { _, _, position ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.flContent, getPositionFragment(position)).commit()
        }
    }

    private fun initRecyclerView() {

        simpleSwitchBinding.rvTop.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        simpleSwitchBinding.rvTop.addItemDecoration(CommonItemDecoration().apply {
            boundary = 20
            interval = 15
        })
        simpleAdapter = object :
            BaseQuickAdapter<String, BaseViewHolder>(R.layout.common_item_horizontal_simple_text) {
            override fun convert(holder: BaseViewHolder, item: String) {
                holder.setText(R.id.tvName, item)
            }

        }
        simpleSwitchBinding.rvTop.adapter = simpleAdapter
    }
}