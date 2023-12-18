package com.xxd.thread.rxjava

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.util.log.LogUtil
import com.xxd.thread.databinding.ThreadFragmentRxjavaBasicBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.HttpURLConnection
import java.net.URL

/**
 *    author : xxd
 *    date   : 2020/9/29
 *    desc   : RxJava的基本使用
 */
class RxJavaBasicFragment : BaseFragment() {

    companion object {
        // 测试下载的图片
        const val IMG_PATH =
            "https://img.rongcat.net/images/2020/07/21/Ta1bnTI3050B1azAGSeuLvhRhq2sP1dI8Pc95lKG.jpeg?width=800&height=800"
    }

    private lateinit var viewBinding : ThreadFragmentRxjavaBasicBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ThreadFragmentRxjavaBasicBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()
        viewBinding.tv1.setOnClickListener {
            downloadImage2()
        }
        viewBinding.tv2.setOnClickListener {
            viewBinding.ivBitmap.setImageBitmap(null)
        }


        doA<String,BaseQuickAdapter<String,BaseViewHolder>>(A("",Thread()))
    }

    class A<T, in R>(t: T, r: R) {

    }

    fun <C, B> doA(a: A<out String, *>) {

    }

    /**
     * 通过RxJava的Single方式流式下载图片
     */
    private fun downloadImage1() {
        val subscribe = Single.just(IMG_PATH)
            .map(object : Function<String, Bitmap> {
                override fun apply(t: String): Bitmap {
                    val url = URL(t)
                    val openConnection = url.openConnection() as HttpURLConnection
                    openConnection.connectTimeout = 5000
                    val responseCode = openConnection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        val inputStream = openConnection.inputStream
                        return BitmapFactory.decodeStream(inputStream)
                    }
                    throw NullPointerException("url=${t}转换图片失败\nresponseCode=${responseCode}")
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                if (t2 != null) {
                    LogUtil.e(t2.localizedMessage ?: "Crash with Empty")
                } else {
                    viewBinding.ivBitmap.setImageBitmap(t1)
                }
            }
    }

    /**
     * 可以把 泛型中的上游，转换为下游，一般用来封装各种
     */
    val translate =
        ObservableTransformer<Bitmap, Bitmap> { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    private fun <UP : Any> getTransformer(): ObservableTransformer<UP, UP> {
        return ObservableTransformer<UP, UP> { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }


    var progressDialog: ProgressDialog? = null

    /**
     * 使用Observable的的方式使用RxJava最早的使用方式
     */
    private fun downloadImage2() {
        Observable.just(IMG_PATH)
            .map(object : Function<String, Bitmap> {
                override fun apply(t: String): Bitmap {
                    val url = URL(t)
                    val openConnection = url.openConnection() as HttpURLConnection
                    openConnection.connectTimeout = 5000
                    Thread.sleep(2000)
                    val responseCode = openConnection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        val inputStream = openConnection.inputStream
                        return BitmapFactory.decodeStream(inputStream)
                    }
                    throw NullPointerException("url=${t}转换图片失败\nresponseCode=${responseCode}")
                }
            })
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
            .compose(getTransformer())
            .subscribe(object : Observer<Bitmap> {
                override fun onSubscribe(d: Disposable) {
                    progressDialog = ProgressDialog(context)
                    progressDialog!!.setTitle("等待图片下载")
                    progressDialog!!.show()
                }

                override fun onNext(t: Bitmap) {
                    viewBinding.ivBitmap.setImageBitmap(t)
                }

                override fun onError(e: Throwable) {
                    progressDialog?.dismiss()
                    Toast.makeText(context, "下载图片是失败：${e.stackTrace}", Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {
                    progressDialog?.dismiss()
                }

            })

    }
}