package com.xxd.coroutine.lifecycle.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.util.toast.ToastUtil
import com.xxd.coroutine.databinding.CoroutineActivityLifecyclelBinding
import com.xxd.coroutine.databinding.CoroutineItemLifecycleBinding
import com.xxd.coroutine.utils.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *    author : xxd
 *    date   : 2026/5/11
 *    desc   : 演示 Fragment 中的双重生命周期：Fragment 本身与 View
 *    以及其与 Coroutine 结合使用
 */
class LifecycleFragment : BaseFragment() {

    private val strList = listOf(
        "1. Fragment本身生命周期 (lifecycle)",
        "2. View生命周期 (viewLifecycleOwner)",
        "3. 差异对比与应用场景",
        "4. LifecycleEventObserver监听",
        "5. currentState 状态查看",
        "6. Fragment协程作用域 (lifecycleScope)",
        "7. View协程作用域 (viewLifecycleOwner.lifecycleScope)",
        "8. repeatOnLifecycle 最佳实践"
    )

    private var _binding: CoroutineActivityLifecyclelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CoroutineActivityLifecyclelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        binding.rv1.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = object : BaseBindingQuickAdapter<String, CoroutineItemLifecycleBinding>() {
                override fun convert(holder: BaseBindingViewHolder<CoroutineItemLifecycleBinding>, item: String) {
                    holder.binding.tv1.text = item
                }
            }.apply {
                setList(strList)
                setOnItemClickListener { _, _, position ->
                    try {
                        val method = this@LifecycleFragment.javaClass.getDeclaredMethod("m${position + 1}")
                        method.isAccessible = true
                        method.invoke(this@LifecycleFragment)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        ToastUtil.showToast("m${position + 1}方法调用失败！")
                    }
                }
            }
        }
    }

    /**
     * m1: Fragment 本身的生命周期 (onAttach -> onDetach)
     */
    private fun m1() {
        log("m1: 注册 Fragment Lifecycle 观察者")
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                log("Fragment Lifecycle -> onCreate: 实例已创建")
            }
            override fun onDestroy(owner: LifecycleOwner) {
                log("Fragment Lifecycle -> onDestroy: 实例销毁")
            }
        })
    }

    /**
     * m2: Fragment 视图的生命周期 (onViewCreated -> onDestroyView)
     */
    private fun m2() {
        log("m2: 注册 View Lifecycle 观察者")
        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                log("View Lifecycle -> onCreate: 视图已创建")
            }
            override fun onDestroy(owner: LifecycleOwner) {
                log("View Lifecycle -> onDestroy: 视图销毁")
            }
        })
    }

    /**
     * m3: 差异对比
     */
    private fun m3() {
        log("m3: 差异点总结")
        log("1. 存活时长：Fragment 实例生命周期 >= 视图生命周期。")
        log("2. 回退栈场景：Fragment 替换进入回退栈时，View 会被销毁，但 Fragment 实例保留。")
        log("3. 核心建议：操作 UI 相关的观察（LiveData/协程）务必使用 viewLifecycleOwner。")
    }

    /**
     * m4: 生命周期事件观察
     */
    private fun m4() {
        log("m4: 添加 LifecycleEventObserver 到 ViewLifecycle")
        viewLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                log("View Event -> $event")
            }
        })
    }

    /**
     * m5: 状态查看
     */
    private fun m5() {
        log("m5: 当前状态对比")
        log("Fragment State: ${lifecycle.currentState}")
        log("View State: ${viewLifecycleOwner.lifecycle.currentState}")
    }

    /**
     * m6: Fragment 级别的协程作用域
     */
    private fun m6() {
        log("m6: 启动 lifecycleScope 协程 (随 Fragment 销毁取消)")
        lifecycleScope.launch {
            log("Fragment Scope 启动，哪怕 View 销毁了只要 Fragment 还在，它就能运行")
        }
    }

    /**
     * m7: View 级别的协程作用域
     */
    private fun m7() {
        log("m7: 启动 viewLifecycleOwner.lifecycleScope 协程 (随 onDestroyView 取消)")
        viewLifecycleOwner.lifecycleScope.launch {
            log("View Scope 启动，这是 Fragment 中最安全的 UI 处理方式")
        }
    }

    /**
     * m8: repeatOnLifecycle 最佳实践
     */
    private fun m8() {
        log("m8: 在 Fragment 中结合 View 使用 repeatOnLifecycle")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                log("进入 STARTED 状态，模拟收集 UI 数据...")
                try {
                    while (true) {
                        delay(2000)
                        log("数据持续收集中...")
                    }
                } finally {
                    log("离开状态或协程取消")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}