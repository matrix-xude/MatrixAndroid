package com.xxd.common.fast

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.R
import com.xxd.common.base.BaseTitleActivity
import com.xxd.common.tool.CommonItemDecoration
import kotlinx.android.synthetic.main.common_activity_switch_fragment.*

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

    override fun getLayoutId(): Int {
        return R.layout.common_activity_switch_fragment
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

        rvTop.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTop.addItemDecoration(CommonItemDecoration().apply {
            boundary = 20
            interval = 15
        })
        simpleAdapter = object :
            BaseQuickAdapter<String, BaseViewHolder>(R.layout.common_item_horizontal_simple_text) {
            override fun convert(holder: BaseViewHolder, item: String) {
                holder.setText(R.id.tvName, item)
            }

        }
        rvTop.adapter = simpleAdapter
    }
}