package com.xxd.view.iconfont

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.binding
import com.xxd.view.databinding.ViewFragmentIconFontSecondBinding

/**
 *    author : xxd
 *    date   : 2021/8/9
 *    desc   :
 */
class IconFontSecondFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentIconFontSecondBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ViewFragmentIconFontSecondBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.BASE
        super.initView()

    }
}