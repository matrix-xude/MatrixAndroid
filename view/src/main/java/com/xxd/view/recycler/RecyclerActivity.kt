package com.xxd.view.recycler

import android.view.ViewGroup
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.view.databinding.ViewActivityRecyclerBinding

/**
 *    author : xxd
 *    date   : 2020/8/13
 *    desc   : 各种recyclerView效果的综合列表
 */
class RecyclerActivity : BaseTitleActivity() {

    private lateinit var viewBinding: ViewActivityRecyclerBinding

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = ViewActivityRecyclerBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence {
        return "DiffUtil"
    }
}