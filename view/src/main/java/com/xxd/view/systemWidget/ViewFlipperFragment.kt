package com.xxd.view.systemWidget

import android.view.View
import android.widget.TextView
import com.xxd.common.base.BaseFragment
import com.xxd.view.R
import kotlinx.android.synthetic.main.view_fragment_flipper.*

/**
 *    author : xxd
 *    date   : 2020/8/20
 *    desc   :
 */
class ViewFlipperFragment : BaseFragment() {

    private val dataList = listOf(
        "量子力学（Quantum Mechanics），为物理学理论，",
        "是研究物质世界微观粒子运动规律的物理学分支，",
        "主要研究原子、分子、凝聚态物质，以及原子核和基本粒子的结构、性质的基础理论。",
        "它与相对论一起构成现代物理学的理论基础。",
        "量子力学不仅是现代物理学的基础理论之一，而且在化学等学科和许多近代技术中得到广泛应用。"
    )

    override fun getLayoutId(): Int {
        return R.layout.view_fragment_flipper
    }

    override fun initView() {
        super.initView()
        repeat(10){
            val tvSimple = View.inflate(context, R.layout.common_simple_text, null) as TextView
            tvSimple.text = dataList[(Math.random() * dataList.size).toInt()]
            viewFlipper.addView(tvSimple)
        }
    }

}