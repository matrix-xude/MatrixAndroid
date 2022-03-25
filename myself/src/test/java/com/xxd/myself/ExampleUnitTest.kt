package com.xxd.myself

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

//        val list = listOf(1, 3, 5, 77)
        val list = listOf("aa","bb")
        val toJson = Gson().toJson(list, object : TypeToken<List<String>>() {}.type)
        println(toJson)

    }
}