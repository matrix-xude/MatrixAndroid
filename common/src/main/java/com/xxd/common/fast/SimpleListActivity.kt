package com.xxd.common.fast

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.databinding.CommonSimpleRecyclerViewBinding
import com.xxd.common.costom.decoration.CommonItemDecoration

/**
 *    author : xxd
 *    date   : 2020/8/28
 *    desc   : 填充数据只是简单的list类型，暂时没添加head,tail
 */
abstract class SimpleListActivity<T> : BaseTitleActivity() {

    private lateinit var simpleRecyclerBinding: CommonSimpleRecyclerViewBinding

    /**
     * recyclerView的adapter，子类可以拿着去自己实现点击事件等
     */
    protected lateinit var simpleAdapter: BaseQuickAdapter<T, BaseViewHolder>

    /**
     * 获取数据
     */
    abstract fun getDataList(): Collection<T>

    /**
     * 获取子item的LayoutResId
     */
    @LayoutRes
    abstract fun getItemLayoutResId(): Int

    /**
     * 填充数据
     */
    abstract fun convertItem(holder: BaseViewHolder, item: T)


    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        simpleRecyclerBinding = CommonSimpleRecyclerViewBinding.inflate(layoutInflater, rootView)
    }

    override fun initView() {
        super.initView()
        initRecyclerView()
    }

    override fun initData() {
        super.initData()
        simpleAdapter.setList(getDataList())
    }

    private fun initRecyclerView() {

        simpleRecyclerBinding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        simpleRecyclerBinding.recyclerView.addItemDecoration(CommonItemDecoration().apply {
            boundary = 20
            interval = 15
        })
        simpleAdapter = object :
            BaseQuickAdapter<T, BaseViewHolder>(getItemLayoutResId()) {
            override fun convert(holder: BaseViewHolder, item: T) {
                convertItem(holder, item)
            }

        }
        simpleRecyclerBinding.recyclerView.adapter = simpleAdapter
    }


}