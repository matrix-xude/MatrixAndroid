package com.xxd.kt.gson.parse

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

/**
 *    author : xxd
 *    date   : 2021/8/4
 *    desc   :
 */
class StringAdapter : TypeAdapter<String>() {

    override fun write(out: JsonWriter?, value: String?) {
        out?.let { jsonWriter ->
            value?.let {
                jsonWriter.value(it)
            } ?: jsonWriter.nullValue() // 此方法不会写该字段
        }
    }

    override fun read(`in`: JsonReader?): String {
        `in`?.let {
            if (it.peek() == JsonToken.NULL) { // 如果返回nulL，转为""
                it.nextNull()
                return ""
            }
            return it.nextString()
        }
        return ""
    }
}