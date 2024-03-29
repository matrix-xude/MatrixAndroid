package com.xxd.view

import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.fast.SimpleListActivity
import com.xxd.common.util.intent.IntentUtil
import com.xxd.view.animator.AnimatorActivity
import com.xxd.view.bigpic.BigPictureActivity
import com.xxd.view.iconfont.IconFontActivity
import com.xxd.view.material.MaterialDesignActivity
import com.xxd.view.myself.MyselfViewPagerActivity
import com.xxd.view.recycler.RecyclerPagerActivity
import com.xxd.view.systemWidget.SystemWidgetActivity
import com.xxd.view.test.TestEditActivity
import com.xxd.view.third.ThirdPagerActivity

@Route(path = "/view/activity/main")
class MainActivity : SimpleListActivity<String>() {

    private val mDataList = mutableListOf(
        "大图", // 0
        "RecyclerView",
        "系统的view",
        "meter design",
        "自定义的view", // 4
        "icon_font", // 5 阿里通用文本图案
        "第三方控件", // 6
        "终章" // 7
    )

    override fun initView() {
        super.initView()
        val extras = intent.extras
        val stringExtra = intent.getStringExtra("key")
        initListener()
    }

    private fun initListener() {
        simpleAdapter.setOnItemClickListener { _, view, position ->
            when (position) {
                0 -> {
//                    val optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.x.toInt(), view.x.toInt(), view.width, view.height)
                    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "shareView")
                    startActivity(Intent(this, BigPictureActivity::class.java), optionsCompat.toBundle())
                }
                1 -> IntentUtil.startActivity<RecyclerPagerActivity>(this)
                2 -> IntentUtil.startActivity<SystemWidgetActivity>(this)
                3 -> IntentUtil.startActivity<MaterialDesignActivity>(this)
                4 -> IntentUtil.startActivity<MyselfViewPagerActivity>(this)
                5 -> IntentUtil.startActivity<IconFontActivity>(this)
                6 -> IntentUtil.startActivity<ThirdPagerActivity>(this)
                7 -> IntentUtil.startActivity<AnimatorActivity>(this)
            }
        }
    }

    override fun getTitleName(): CharSequence {
        return "View集合"
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

}
