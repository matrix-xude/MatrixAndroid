package com.xxd.view.myself.flow

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isNotEmpty
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.binding
import com.xxd.common.extend.onClick
import com.xxd.view.databinding.ViewFragmentFirstViewGourpBinding

/**
 *    author : xxd
 *    date   : 2021/8/26
 *    desc   :
 */
class FirstViewGroupFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentFirstViewGourpBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = ViewFragmentFirstViewGourpBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()


        viewBinding.tv1.onClick {
            val addTv = TextView(context).apply {
                textSize = 20f
                setTextColor(Color.BLUE)
                text = "我是新加的   哈啊啊"
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
            viewBinding.flowView.addView(addTv)
        }

        viewBinding.tv2.onClick {
            viewBinding.flowView.apply {
                takeIf { isNotEmpty() }?.removeViewAt(childCount - 1)
            }
        }
    }
}