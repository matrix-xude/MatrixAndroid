package com.xxd.view.material.tablayout

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.binding
import com.xxd.view.databinding.ViewFragmentTopicBaseBinding

/**
 *    author : xxd
 *    date   : 2021/7/26
 *    desc   : 话题页面的数据都高度重合，抽取为BaseFragment
 */
class TopicBaseFragment(private val index: Int) : BaseFragment() {

    private var viewBinding by binding<ViewFragmentTopicBaseBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = ViewFragmentTopicBaseBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun initDataLazy() {
        super.initDataLazy()
        viewBinding.tv1.text = "Amber $index"
        Logger.d("我被调用了 Fragment $index")
    }
}