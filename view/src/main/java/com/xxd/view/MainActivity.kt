package com.xxd.view

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.View
import androidx.annotation.IntRange
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.orhanobut.logger.Logger
import com.xxd.common.base.BaseActivity
import com.xxd.common.util.LogUtil
import kotlinx.android.synthetic.main.view_activity_main.*
import okio.Okio

@Route(path = "/view/activity/main")
class MainActivity : BaseActivity() {

    private val mDataList = mutableListOf("生命周期观察", "", "", "", "", "", "", "", "", "", "", "", "")
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
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvMain.layoutManager = manager
        rvMain.addItemDecoration(object : RecyclerView.ItemDecoration(){
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = 15
            }
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
