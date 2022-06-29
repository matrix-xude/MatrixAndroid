package com.xxd.kt.gson.parse

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.xxd.kt.tools.RxDataTool
import java.io.IOException

/**
 *    author : xxd
 *    date   : 2021/8/4
 *    desc   :
 */
class IntAdapter : TypeAdapter<Int>() {

    override fun write(out: JsonWriter?, value: Int?) {
        out?.let { jsonWriter ->
            value?.let {
                jsonWriter.value(it)
            } ?: jsonWriter.nullValue() // 此方法不会写该字段
        }
    }

    override fun read(`in`: JsonReader?): Int {
        `in`?.let {
            if (it.peek() == JsonToken.NULL) { // 如果返回nulL，转为0
                it.nextNull()
                return 0
            }
            if (it.peek() == JsonToken.STRING) {
                val nextString = it.nextString()
                if (nextString.isEmpty()) {
                    return 0
                }
                when {
                    RxDataTool.isInteger(nextString) -> nextString.toInt()
                    RxDataTool.isDouble(nextString) -> {
                        val toDouble = nextString.toDouble()
                        if (RxDataTool.doubleIsInt(toDouble)) {  // 没有小数点后面部分的double,直接返回int
                            return toDouble.toInt()
                        } else {  // 让它抛出异常报错
                            nextString.toInt()
                        }
                    }
                }
            }
            return it.nextInt()
        }
        return 0
    }
}