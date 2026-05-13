package com.xxd.coroutine.lifecycle

import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.costom.decoration.CommonItemDecoration
import com.xxd.common.util.toast.ToastUtil
import com.xxd.coroutine.databinding.CoroutineActivityLifecyclelBinding
import com.xxd.coroutine.databinding.CoroutineItemLifecycleBinding
import com.xxd.coroutine.utils.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *    author : xxd
 *    date   : 2026/5/11
 *    desc   : 研究 lifeCycle 生命周期 的实现与相关组件，如Activity,Fragment，ViewModel
 *    以及其与 Coroutine结合使用, 如 lifecycleScope, viewModelScope
 */
class LifecycleActivity : BaseTitleActivity() {

    private val strList = listOf(
        "LifeCycle基础使用",
        "LifeCycle事件",
        "LifeCycle状态",
        "LifeCycleObserver空接口",
        "LifeCycleOwner作用",
        "LifecycleRegistry是LifeCycle的具体实现类",
        "Activity中LifeCycle通知流程",
        "LifeCycle与Coroutine结合"
    )
    private lateinit var viewBinding: CoroutineActivityLifecyclelBinding

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = CoroutineActivityLifecyclelBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence {
        return "Lifecycle及相关组件研究"
    }

    override fun initView() {
        super.initView()

        viewBinding.rv1.apply {
            layoutManager = LinearLayoutManager(this@LifecycleActivity, RecyclerView.VERTICAL, false)
            addItemDecoration(CommonItemDecoration().apply {
                boundary = 20
                interval = 15
            })
            adapter = object : BaseBindingQuickAdapter<String, CoroutineItemLifecycleBinding>() {
                override fun convert(holder: BaseBindingViewHolder<CoroutineItemLifecycleBinding>, item: String) {
                    holder.binding.tv1.text = item
                }
            }.apply {
                setList(strList)
                setOnItemClickListener { _, _, position ->
                    // 反射调用 m1,m2……方法，方便
                    try {
                        // 使用 getDeclaredMethod 因为方法是 private 的
                        val method = this@LifecycleActivity.javaClass.getDeclaredMethod("m${position + 1}")
                        method.isAccessible = true
                        method.invoke(this@LifecycleActivity)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        ToastUtil.showToast("m${position + 1}方法调用失败或未创建！")
                    }
                }
            }
        }
    }

    /**
     * LifeCycle基础使用：使用 DefaultLifecycleObserver 观察生命周期
     */
    private fun m1() {
        log("m1: 添加 DefaultLifecycleObserver")
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                log("DefaultLifecycleObserver -> onCreate: ${owner.javaClass.simpleName}")
            }

            override fun onStart(owner: LifecycleOwner) {
                log("DefaultLifecycleObserver -> onStart")
            }

            override fun onResume(owner: LifecycleOwner) {
                log("DefaultLifecycleObserver -> onResume")
            }
        })
    }

    /**
     * LifeCycle事件：使用 LifecycleEventObserver 监听所有生命周期事件
     */
    private fun m2() {
        log("m2: 添加 LifecycleEventObserver")
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                log("LifecycleEventObserver -> onStateChanged: event = $event")
            }
        })
    }

    /**
     * LifeCycle状态：查看当前 Lifecycle 的状态
     */
    private fun m3() {
        val currentState = lifecycle.currentState
        log("m3: 当前生命周期状态 currentState = $currentState")
        // Lifecycle.State 包含: DESTROYED, INITIALIZED, CREATED, STARTED, RESUMED
    }

    /**
     * LifeCycleObserver空接口：源码查看建议
     */
    private fun m4() {
        log("m4: LifecycleObserver 是一个空接口，用于标记类为生命周期观察者。")
        log("源码入口建议：查看 androidx.lifecycle.LifecycleObserver")
        log("常用子类：DefaultLifecycleObserver (推荐), LifecycleEventObserver")
    }

    /**
     * LifeCycleOwner作用：谁持有生命周期
     */
    private fun m5() {
        log("m5: LifecycleOwner 是一个单一方法接口，表示类具有 Lifecycle")
        log("源码入口建议：查看 androidx.lifecycle.LifecycleOwner")
        log("Activity (ComponentActivity) 和 Fragment 都实现了这个接口。")
    }

    /**
     * LifecycleRegistry是LifeCycle的具体实现类
     */
    private fun m6() {
        log("m6: LifecycleRegistry 是 Lifecycle 的具体实现类。")
        log("它内部维护了一个 FastSafeIterableMap 来存储观察者，并处理状态迁移。")
        log("源码入口建议：查看 androidx.lifecycle.LifecycleRegistry")
    }

    /**
     * Activity中LifeCycle通知流程：生命周期是如何分发的
     */
    private fun m7() {
        log("m7: 生命周期分发的核心类是 ReportFragment。")
        log("ComponentActivity 在 onCreate() 中调用 ReportFragment.injectIfNeededIn(this) 将其注入。")
        log("ReportFragment 捕获 Activity 生命周期事件，并分发给 LifecycleRegistry。")
        log("源码入口建议：查看 androidx.lifecycle.ReportFragment")
    }

    /**
     * LifeCycle与Coroutine结合：lifecycleScope 和 repeatOnLifecycle
     */
    private fun m8() {
        log("m8: 开始展示 lifecycleScope 和 repeatOnLifecycle")
        // lifecycleScope 在 Lifecycle 销毁时自动取消
        lifecycleScope.launch {
            log("lifecycleScope -> 协程启动")
            
            // repeatOnLifecycle 会在进入对应状态时执行，离开时挂起/取消
            // 建议源码查看：androidx.lifecycle.RepeatOnLifecycleKt
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                log("repeatOnLifecycle -> 进入 STARTED 状态")
                var count = 0
                while (true) {
                    delay(1000)
                    log("repeatOnLifecycle -> 计数中: ${++count}")
                }
            }
            // 注意：当 Lifecycle 变为 DESTROYED 时，上面的 block 会被取消，
            // 且 repeatOnLifecycle 下方的代码只有在 Lifecycle 销毁后才可能（如果是挂起函数）继续执行（通常不会执行了，因为 scope 已经取消）
            log("lifecycleScope -> 协程结束") 
        }
    }
}