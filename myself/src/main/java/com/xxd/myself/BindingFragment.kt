package com.xxd.myself

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.binding
import com.xxd.myself.databinding.MyselfFragmentTestBinding

/**
 * author : xxd
 * date   : 2021/7/4
 * desc   :
 */
class BindingFragment : BaseFragment() {

    private var binding by binding<MyselfFragmentTestBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MyselfFragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        super.initView()
        binding.in3.tv3.paint.isFakeBoldText = true
    }


    override fun initDataImmediately() {
        super.initDataImmediately()
        binding.tv1.text = "好人啊"
    }
}