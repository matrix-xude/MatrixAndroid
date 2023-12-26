package com.xxd.myself.dp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.onClick
import com.xxd.myself.databinding.MyselfFragmentSmallestWidthBinding

/**
 *    author : xxd
 *    date   : 2023/12/26
 *    desc   : 最小宽度适配方案
 */
class SmallestWidthFragment : BaseFragment() {

    private lateinit var viewBinding: MyselfFragmentSmallestWidthBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = MyselfFragmentSmallestWidthBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        initListener()
    }


    private fun initListener() {
        viewBinding.tv1.onClick {
            viewBinding.tv1.text = viewBinding.tv1.width.toString()
        }
        viewBinding.tv2.onClick {
            viewBinding.tv2.text = viewBinding.tv2.width.toString()
        }
        viewBinding.tv3.onClick {
            viewBinding.tv3.text = viewBinding.tv3.width.toString()
        }
        viewBinding.tv4.onClick {
            viewBinding.tv4.text = viewBinding.tv4.width.toString()
        }
    }



}