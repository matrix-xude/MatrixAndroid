package com.xxd.thread.rxjava

import org.junit.Test

import org.junit.Assert.*

/**
 * author : xxd
 * date   : 2020/10/19
 * desc   :
 */
class RxJavaTest {

    @Test
    fun rxZip() {
        val zipTest = RxJavaZipTest()
        zipTest.rxZip()
    }

    @Test
    fun rxFlatMap() {
        val zipTest = RxJavaZipTest()
        zipTest.flatMap()
    }

}