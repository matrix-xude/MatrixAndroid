package com.xxd.view.material.flexboxlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.binding
import com.xxd.view.databinding.ViewFragmentFlexBoxLayoutBinding

/**
 *    author : xxd
 *    date   : 2021/9/9
 *    desc   :
 */
class FlexboxLayoutFragment() : BaseFragment() {

    private var viewBinding by binding<ViewFragmentFlexBoxLayoutBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ViewFragmentFlexBoxLayoutBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()


    }


}