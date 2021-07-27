package com.xxd.view.material.tablayout

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.binding
import com.xxd.view.databinding.ViewFragmentTabLayoutBinding

/**
 *    author : xxd
 *    date   : 2020/8/20
 *    desc   : 修改TabLayout
 */
class TabLayoutFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentTabLayoutBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = ViewFragmentTabLayoutBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun initDataLazy() {
        super.initDataLazy()
        viewBinding.vpTopic.adapter = VPAdapter(this)
        viewBinding.vpTopic.offscreenPageLimit =3

        TabLayoutMediator(viewBinding.tlTopic, viewBinding.vpTopic) { tap, positon2 ->
            tap.text = "话题${if(positon2%2==0) "" else ""}$positon2"
        }.attach()
    }


}