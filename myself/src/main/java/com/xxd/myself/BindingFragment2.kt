package com.xxd.myself

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.orhanobut.logger.Logger
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.binding
import com.xxd.common.module.login.LoginOwner
import com.xxd.common.util.toast.ToastUtil
import com.xxd.myself.databinding.MyselfFragmentMain2Binding
import com.xxd.myself.databinding.MyselfFragmentTestBinding

/**
 * author : xxd
 * date   : 2021/7/4
 * desc   :
 */
class BindingFragment2 : BaseFragment() {

    var binding by binding<MyselfFragmentMain2Binding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyselfFragmentMain2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initDataImmediately() {
        super.initDataImmediately()
        binding.tv1.text = "切换后的Fragment"

        val model by viewModels<FragmentViewModel>()

        LoginOwner.instance.status().observe(this) {
            ToastUtil.showToast("当前登录状态： $it")
        }
    }
}