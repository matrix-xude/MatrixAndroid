package com.xxd.myself.navigation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.onClick
import com.xxd.myself.R
import com.xxd.myself.databinding.MyselfFragmentNavigationBinding

/**
 *    author : xxd
 *    date   : 2024/3/16
 *    desc   : Navigation导航的Fragment
 */
class NavigationFragment2 : BaseFragment() {

    private lateinit var viewBinding: MyselfFragmentNavigationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = MyselfFragmentNavigationBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        initListener()

        viewBinding.tv1.run {
            text = "我是第2个Fragment"
            setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.common_orchid))
        }


    }

    private fun initListener() {
        viewBinding.tv2.onClick {
            findNavController().navigate(R.id.myself_navigationfragment3)
        }
        viewBinding.tv3.onClick {
            findNavController().navigate(R.id.myself_navigationfragment3, null, navOptions {
                popUpTo(R.id.myself_navigationfragment1){
                    inclusive = true
                }
            })

        }
        viewBinding.tv4.onClick {

        }
    }

}