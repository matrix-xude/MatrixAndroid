package com.xxd.thread.encode

import org.junit.Before
import org.junit.Test

/**
 * author : xxd
 * date   : 2020/9/21
 * desc   :
 */
class EncodeTestTest {

    lateinit var mTest: EncodeTest

    @Before
    fun initTest(){
        mTest = EncodeTest()
    }

    @Test
    fun test1() {
        mTest.test1()
    }

    @Test
    fun test2() {
        mTest.test2()
    }

    @Test
    fun test3() {
        mTest.test3()
    }
}