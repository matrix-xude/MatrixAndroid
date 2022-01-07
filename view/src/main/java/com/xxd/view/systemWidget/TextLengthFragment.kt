package com.xxd.view.systemWidget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.binding
import com.xxd.common.extend.onClick
import com.xxd.view.R
import com.xxd.view.databinding.ViewFragmentLengthBinding

/**
 *    author : xxd
 *    date   : 2020/8/20
 *    desc   : 测试TextView的宽度自适应的问题
 */
class TextLengthFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentLengthBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ViewFragmentLengthBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

        viewBinding.tv1.onClick {
            viewBinding.tv4.text = "发辅导辅导费"
            viewBinding.tv5.text = "发发到付"
        }

        viewBinding.tv2.onClick {
            viewBinding.tv4.text = "发辅导辅导费发辅导辅导费发辅导辅导费发辅导辅导费发辅导辅导费发辅导辅导费发辅导辅导费"
            viewBinding.tv5.text = "发发发辅导辅导费发辅导辅导费发辅导辅导费发辅导辅导费发辅导辅导费发辅导辅导费发辅导辅导费发辅导辅导费到付"
        }
    }


}