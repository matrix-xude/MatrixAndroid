package com.xxd.view.myself.oneline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.binding
import com.xxd.view.databinding.ViewFragmentOneLineBinding

/**
 *    author : xxd
 *    date   : 2021/8/26
 *    desc   :
 */
class OneLineLayoutFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentOneLineBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = ViewFragmentOneLineBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()
    }
}