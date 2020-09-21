package com.xxd.thread.encode

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * author : xxd
 * date   : 2020/9/21
 * desc   :
 */
class DeepTest {

    lateinit var mDeep : EncodeDeep

    @Before
    fun init(){
        mDeep = EncodeDeep()
    }

    @Test
    fun encodeFile() {
        mDeep.encodeFile()
    }

    @Test
    fun decodeFile() {
        mDeep.decodeFile()
    }
}