package com.xxd.thread.encode

import org.junit.Assert.*
import org.junit.Test

/**
 * author : xxd
 * date   : 2020/9/20
 * desc   :
 */
class ObjectEncodeTest{

    @Test
    fun fileEncode(){
        val objectEncodeTest = ObjectEncodeFragment()
        objectEncodeTest.encodeFile()
    }

    @Test
    fun fileDecode(){
        val objectEncodeTest = ObjectEncodeFragment()
        objectEncodeTest.decodeFile()

    }


}