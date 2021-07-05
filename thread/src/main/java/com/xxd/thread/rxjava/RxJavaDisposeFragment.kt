package com.xxd.thread.rxjava

import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.util.log.LogUtil
import com.xxd.thread.R
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.thread_fragment_rxjava_dispose.*

/**
 * author : xxd
 * date   : 2020/10/7
 * desc   :
 */
class RxJavaDisposeFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.thread_fragment_rxjava_dispose
    }

    override fun initView() {
        super.initView()
        tv1.setOnClickListener {
            testCreateDispose()
        }
        tv2.setOnClickListener {
            testDisposeCreate()
        }
        tv3.setOnClickListener {
            testReturnDispose()
        }
        tv4.setOnClickListener {
            testDisposeReturn()
        }
        tv5.setOnClickListener {
            prepareObservable()
        }
        tv6.setOnClickListener {
            beginSubscribe()
        }
    }

    // 通过创建 SingleObserver 中的 onSubscribe 来赋值的
    private var createDisposable: Disposable? = null

    // 通过 subscribe 中的 返回值 来赋值的
    private var returnDisposable: Disposable? = null

    /**
     * 在 onSubscribe 方法中赋值，是可以用来中断执行的
     */
    private fun testCreateDispose() {
        Observable.create(ObservableOnSubscribe<Int> { emitter ->
            emitter?.onNext(1)
            emitter?.onNext(2)
            emitter?.onNext(3)
        })
            .map {
                logDisposable(createDisposable)
                Thread.sleep(1000)
                LogUtil.d("第一个map 执行了 $it")
                it.toString()
            }
            .map {
                logDisposable(createDisposable)
                Thread.sleep(1000)
                LogUtil.d("第二个map 执行了 $it")
                "${it}——增加"
            }
            .map {
                logDisposable(createDisposable)
                Thread.sleep(1000)
                LogUtil.d("第三个map 执行了 $it")
                "${it}——再来一个"
            }
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable?) {
                    createDisposable = d
                    LogUtil.d("Disposable 赋值执行了")
                }

                override fun onError(e: Throwable?) {
                    e?.printStackTrace()
                }

                override fun onNext(t: String?) {
                    logDisposable(createDisposable)
                    LogUtil.d("onNext 执行了 $t")
                }

                override fun onComplete() {
                    LogUtil.d("onComplete 执行了")
                }
            })
    }

    private fun testDisposeCreate() {
        LogUtil.d("我点了中断Disposable按钮")
        createDisposable?.dispose()
    }

    /**
     * 在返回值中获取赋值，执行完所有才能拿到赋值的Disposable
     */
    private fun testReturnDispose() {
        returnDisposable = Observable.just(2, 3, 4)
            .map {
                logDisposable(returnDisposable)
                Thread.sleep(1000)
                LogUtil.d("第一个map 执行了 $it")
                it.toString()
            }
            .map {
                logDisposable(returnDisposable)
                Thread.sleep(1000)
                LogUtil.d("第二个map 执行了 $it")
                "${it}——增加"
            }
            .map {
                logDisposable(returnDisposable)
                Thread.sleep(1000)
                LogUtil.d("第三个map 执行了 $it")
                "${it}——再来一个"
            }
            .subscribe({
                it?.let {
                    logDisposable(returnDisposable)
                    LogUtil.d("onNext 执行了 $it")
                }
            }, {
                it?.printStackTrace()
            }, {
                LogUtil.d("onComplete 执行了")
            })

    }

    private fun testDisposeReturn() {
        returnDisposable?.dispose()
    }

    // 打印Disposable是否已经被赋值
    private fun logDisposable(d: Disposable?) {
        LogUtil.d(d?.let { "${it}——已经被赋值" } ?: "当前Disposable还未被赋值")
    }

    var subscribe: Observable<String>? = null
    var observer: Observer<String>? = null
    var disposable : Disposable? = null

    private fun prepareObservable() {
        subscribe = Observable.just(1, 2, 3)
            .map {
                "${it}变换了"
            }

        observer = object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
                disposable = d
                LogUtil.d("onSubscribe $d")
            }

            override fun onNext(t: String?) {
                LogUtil.d("onNext $t")
            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()
            }

            override fun onComplete() {
                LogUtil.d("onComplete")
                disposable?.dispose()
            }
        }
    }

    private fun beginSubscribe() {
        subscribe?.subscribe(observer)
    }
}