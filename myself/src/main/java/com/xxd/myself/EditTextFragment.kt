package com.xxd.myself

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.myself.databinding.MyselfFragmentEditTextBinding

/**
 * author : xxd
 * date   : 2021/7/22
 * desc   :
 */
class EditTextFragment : BaseFragment() {

    lateinit var binding: MyselfFragmentEditTextBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyselfFragmentEditTextBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}