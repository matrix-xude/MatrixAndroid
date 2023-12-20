package com.xxd.thread.rxjava

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.costom.decoration.CommonItemDecoration
import com.xxd.common.databinding.CommonItemSimpleTextBinding
import com.xxd.common.extend.onClick
import com.xxd.thread.databinding.ThreadFragmentRxjavaTypeBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.AsyncSubject
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.Arrays
import java.util.concurrent.TimeUnit

/**
 *    author : xxd
 *    date   : 2023/12/18
 *    desc   :
 */
class RxJavaTypeFragment : BaseFragment() {

    private lateinit var viewBinding: ThreadFragmentRxjavaTypeBinding
    private lateinit var adapter: BaseBindingQuickAdapter<String, CommonItemSimpleTextBinding>
    private val list = listOf("Observable1", "Observable2", "Single", "Completable", "Maybe", "Flowable")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ThreadFragmentRxjavaTypeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        initRecyclerView()
    }

    override fun initDataLazy() {
        adapter.setList(list)
    }

    private fun initRecyclerView() {
        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this@RxJavaTypeFragment.adapter = object : BaseBindingQuickAdapter<String, CommonItemSimpleTextBinding>() {
                override fun convert(holder: BaseBindingViewHolder<CommonItemSimpleTextBinding>, item: String) {
                    holder.binding.tvName.text = item
                }

                override fun onItemViewHolderCreated(viewHolder: BaseBindingViewHolder<CommonItemSimpleTextBinding>, viewType: Int) {
                    super.onItemViewHolderCreated(viewHolder, viewType)

                    viewHolder.binding.tvName.onClick {
                        when (viewHolder.bindingAdapterPosition) {
                            0 -> typeObservable()
                            1 -> typeObservable2()
                            2 -> typeSingle()
                            3 -> typeCompletable()
                            4 -> typeMaybe()
                            5 -> typeFlowable()
                        }
                    }
                }
            }
            adapter = this@RxJavaTypeFragment.adapter

            addItemDecoration(CommonItemDecoration().apply {
                boundary = 15
                interval = 20
            })
        }
    }

    fun typeObservable() {

        val observable = Observable.create {
            it.onNext(1)
            it.onNext(2)
            it.onNext(0)
            it.onNext(10)
            it.onComplete()
        }.map {
            Thread.sleep(100)
            it / 10
        }

        // cold流，每次都接收全部事件
        val dispose1 = observable
            .subscribe({ onNext: Int ->
                Logger.d("currentThread : ${Thread.currentThread().name} -> onNext=$onNext")
            }, { throwable: Throwable ->
                Logger.e(throwable, "currentThread : ${Thread.currentThread().name}")
            }, { ->
                Logger.d("currentThread : ${Thread.currentThread().name} -> onComplete")
            })

        val dispose2 = observable.subscribe({ onNext: Int ->
            Logger.d("currentThread2 : ${Thread.currentThread().name} -> onNext=$onNext")
        }, { t2: Throwable ->
            Logger.e(t2, "currentThread2 : ${Thread.currentThread().name}")
        })
    }

    fun typeObservable2() {
        RxJavaPlugins.setOnObservableAssembly {
            Logger.d("RxJavaPlugins OnObservableAssembly hook到了")
            it
        }
        RxJavaPlugins.setErrorHandler {// 如果代码内部处理了异常，全局是抓不到异常的
            Logger.d("我收到一个异常：${it.cause}")
        }

        val disposable = Observable.just(1, 2, )
            .map {
                Logger.d("currentThread : ${Thread.currentThread().name} -> just：$it")
                it * 2
            }
            .flatMap {
                Observable.range(0, it)
            }
            .map {
                Logger.d("currentThread : ${Thread.currentThread().name} -> map：$it")
                it + 1000
            }
            .subscribeOn(Schedulers.io())  // 调用器，打包source过程中使用，subscribeActual()方法中执行
            .observeOn(AndroidSchedulers.mainThread())  // 调度器,接收中使用, onNext()方法中执行
            .subscribe({ onNext ->
                Logger.d("currentThread : ${Thread.currentThread().name} -> onNext：$onNext")
            }, { throwable ->
                Logger.e(throwable, "currentThread2 : ${Thread.currentThread().name}")
            })
    }

    fun typeSingle() {
        val disposable = Single.just(1)  // Single只能发射一个事件，只有success,error2个结果
            .map { it * 3 }
            .subscribe { onSuccess, onError ->
                onSuccess?.let {
                    Logger.d("Single onSuccess -> $onSuccess")
                }
                onError?.let {
                    Logger.e(onError, "Single onError")
                }
            }
    }

    fun typeCompletable() {
        val disposable = Completable.create {// Completable不能发出事件，只有complete，error2个结果
            Logger.d("我是Completable")
            it.onComplete()
        }.subscribe({ ->
            Logger.d("Completable onComplete -> ")
        }, { onError ->
            Logger.e(onError, "Completable onError")
        })
    }

    fun typeMaybe() {
        val dispose = Maybe.just(1) // Maybe发出0-1个事件，只有success,error,complete 3种结果，且3种互斥
            .map {
                it * 2
            }
            .delay(2, TimeUnit.SECONDS)
            .subscribe({ onSuccess: Int ->
                Logger.d("Maybe onSuccess -> $onSuccess")
            }, { throwable: Throwable ->
                Logger.e(throwable, "Maybe onError")
            }, {
                Logger.d("Maybe onComplete -> ")
            })

//        dispose.dispose()  // 取消后3种事件都收不到
    }

    fun typeFlowable() {
        val disposable = Flowable.range(0,1000)
            .onBackpressureBuffer(1000) // 可以指定缓存大小
            .onBackpressureDrop()  // 可以指定背压策略
            .map {
                Logger.d("当前map -> it=$it")
                it
            }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<Int> {
                override fun onSubscribe(s: Subscription?) {
                    s?.request(1000)  // 可以主动请求发出的参数
                }

                override fun onError(t: Throwable?) {
                    Logger.e(t, "Flowable onError")
                }

                override fun onComplete() {
                    Logger.d("Flowable onComplete -> ")
                }

                override fun onNext(t: Int?) {
                    Logger.d("Flowable onNext -> $t")
                }
            })
    }
}

