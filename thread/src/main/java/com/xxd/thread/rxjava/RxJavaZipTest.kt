package com.xxd.thread.rxjava

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

/**
 *    author : xxd
 *    date   : 2020/10/19
 *    desc   : 测试RxJava的 concat,merger,zip
 */
class RxJavaZipTest {

    /**
     * 测试RxJava中的 zip 操作符
     * 不同流合并结果的操作符，常用于数据来源不同，展示结果需要2个数据源合并的地方（如：请求2个接口的数据合并展示）
     */
    fun rxZip() {
        val single1 = Single.just(3)
//            .observeOn(Schedulers.io())
            .map {
                Thread.sleep(it * 1000.toLong())
                println("Single1 睡眠${it}秒之后")
                it
            }

        val single2 = Single.just(4)
//            .observeOn(Schedulers.io())
            .map {
                Thread.sleep(it * 1000.toLong())
                println("Single2 睡眠${it}秒之后")
                it
            }

        Single.zip(single1, single2, { i: Int, j: Int ->
            i + j
        })
//            .subscribeOn(Schedulers.io())
            .subscribe { t1, t2 ->
                t1?.let {
                    println("zip流转后的结果 $it")
                }
                t2?.let {
                    it.printStackTrace()
                }
            }

        // 保持主线程不会死亡
        Thread.sleep(10000)

    }

    fun flatMap() {
        Observable.just(3)
            .flatMap { i ->
                val listOf = (1..i).toList()
                Observable.fromIterable(listOf)
            }
            .observeOn(Schedulers.io())
            .map {
                Thread.sleep(1000)
                it
            }
            .subscribe {
                println(it)
            }

        Thread.sleep(4000)
    }

}