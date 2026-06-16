package com.xxd.coroutine.vm

import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.extend.onClick
import com.xxd.coroutine.databinding.CoroutineActivityVmBinding
import com.xxd.coroutine.vm.models.AppViewModel
import com.xxd.coroutine.vm.models.MyParamViewModel
import com.xxd.coroutine.vm.models.NoParamViewModel
import com.xxd.coroutine.vm.models.SavedStateViewModel

/**
 *    author : xxd
 *    date   : 2026/5/25
 *    desc   : 探索ViewModel的各种用法
 */
class SearchVMActivity : BaseTitleActivity() {

    private lateinit var viewBinding: CoroutineActivityVmBinding

    private val noParamVM by viewModels<NoParamViewModel>()
    private val applicationVM by viewModels<AppViewModel>()
    private val savedStateVM by viewModels<SavedStateViewModel>()

    // 使用CreationExtras创建自定义参数ViewModel，增加解耦与复用性
    private val myParamVM by viewModels<MyParamViewModel>(
        extrasProducer = {
            MutableCreationExtras(defaultViewModelCreationExtras).apply {
                set(MyParamViewModel.DESC_KEY, "从Extras注入")
                set(MyParamViewModel.COUNT_KEY, 888)
            }
        },
        factoryProducer = { MyParamViewModel.Factory }
    )
    // 普通自定义factoryProducer创建自定义参数ViewModel,适合简单的一次性使用ViewModel（注意：这里参数不生效，因为已经有了ViewModelStoreOwner缓存）
    private val myParamVM2 by viewModels<MyParamViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MyParamViewModel("自创", 99) as T
                }
            }
        }
    )

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = CoroutineActivityVmBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence? {
        return "研究ViewModel的各种用法"
    }

    override fun initView() {
        viewBinding.btnNoParamVm.onClick {
            noParamVM.testCall()
        }
        viewBinding.btnAppVm.onClick {
            applicationVM.testCall()
        }
        viewBinding.btnMyParamVm.onClick {
            myParamVM.testCall()
            myParamVM2.testCall()  // 测试 ViewModelStoreOwner 缓存是否生效
        }
        viewBinding.btnSavedStateVm.onClick {
            savedStateVM.testCall()
        }
    }
}