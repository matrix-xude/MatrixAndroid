package com.xxd.myself.flow

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.xxd.common.base.activity.BaseLogicActivity
import com.xxd.common.extend.onClick
import com.xxd.common.util.log.LogUtil
import com.xxd.common.util.toast.ToastUtil
import com.xxd.myself.databinding.MyselfActivityFlowBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 *    author : xxd
 *    date   : 2022/2/7
 *    desc   :
 */
class FlowActivity : BaseLogicActivity() {

    private lateinit var viewBinding: MyselfActivityFlowBinding
    private val viewModel by viewModels<FlowViewModel>()

    override fun getContentViewBase(): View {
        viewBinding = MyselfActivityFlowBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

        viewBinding.tv1.onClick {
            viewModel.uiState.value = AAA(1, "张三")
//            lifecycleScope.launch {
//            }
        }
        viewBinding.tv2.onClick {
            it.postDelayed({
            viewModel.uiState.value = AAA(2, "张三")

            },1)
//            lifecycleScope.launch {
//                val tryEmit = viewModel.uiState.emit(3)
//            }
        }

        viewBinding.tv3.onClick {
            LogUtil.d(viewModel.uiState.replayCache)
        }
    }

    override fun initData() {
        super.initData()
        testStateFlow3()


    }

    private fun testStateFlow1() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiShireFlow
//                    .distinctUntilChanged()
                    .collect {
//                        LogUtil.d("接收到的first数据$it")
                        ToastUtil.showToast("当前数据是$it")
                    }

            }
        }
    }

    private fun testStateFlow3() {
        lifecycleScope.launch {
            whenStarted {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    LogUtil.d("接收到的first数据$it")
                ToastUtil.showToast("当前数据是$it")
                }
            }
        }
    }

    private fun testStateFlow2() {
        lifecycleScope.launch {
            viewModel.uiState.first {
                LogUtil.d("接收到的first数据$it")
                ToastUtil.showToast("当前数据是$it")
                true
            }
        }

        lifecycleScope.launch {
            val last = viewModel.uiState.last()
            LogUtil.d("接收到的last数据$last")
        }
    }

    private fun testFlow() {
        lifecycleScope.launch {
            viewModel.flow.first {
                LogUtil.d("接收到的first数据$it")
                ToastUtil.showToast("当前数据是$it")
                true
            }
        }

        lifecycleScope.launch {
            val last = viewModel.flow.last()
            LogUtil.d("接收到的last数据$last")
        }
    }

}