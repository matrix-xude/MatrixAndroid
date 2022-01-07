package com.xxd.thread.encode

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.annotations.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.xxd.common.base.fragment.BaseFragment
import java.text.SimpleDateFormat
import java.util.regex.Pattern

/**
 *    author : xxd
 *    date   : 2020/9/23
 *    desc   :
 */
class JsonFragment : BaseFragment() {

    private val gson = Gson()

    class JsonA {
        var a: Int = 0
        var name: String? = null
    }

    /**
     * 测试不完整的json能否转换
     */
    fun test1() {
        val toJson1 = gson.toJson(1)
        val toJson2 = gson.toJson("abc")
        val toJson3 = gson.toJson(JsonA().apply { a = 1;name = "张三" })
        println("不完整的json1 $toJson1")
        println("不完整的json2 $toJson2")
        println("完整的json3 $toJson3")

        /*
        不完整的json1~1
        不完整的json2~"abc"
        完整的json3~{"a":1,"name":"张三"}
        */
    }

    class JsonB(
        @SerializedName("index") var a: Int,
        var name: String?
    ) {
        override fun toString(): String {
            return "JsonB(a=$a, name=$name)"
        }
    }

    /**
     * 测试Gson序列化中常用的注解
     * SerializedName 使用此注解的value的序列化和反序列化字段，常用于字段名变更、防止被混淆
     */
    fun test2() {
        val b = JsonB(2, "泡利")
        val toJson = gson.toJson(b)
        println(toJson)

        val fromJson = gson.fromJson(toJson, JsonB::class.java)
        println(fromJson)

        /*
            {"index":2,"name":"泡利"}
            JsonB(a=2, name=泡利)
        */
    }

    class JsonC(
        var a: Int,
        @Expose(serialize = true, deserialize = false) var name: String?,
        @Expose(serialize = true, deserialize = true) var matrix: String?
    ) {

        override fun toString(): String {
            return "JsonC(a=$a, name=$name, matrix=$matrix)"
        }
    }

    /**
     * 测试Gson序列化中常用的注解
     * Expose {@link GsonBuilder().excludeFieldsWithoutExposeAnnotation()}必须使用此方法创建才能生效
     */
    fun test3() {
        val b = JsonC(2, "泡利", "AB")

        // 排除不使用Expose的注解，Expose 默认2个值都为true,
        val gson2 = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        val toJson2 = gson2.toJson(b)
        println(toJson2)

        val fromJson2 = gson2.fromJson(toJson2, JsonC::class.java)
        println(fromJson2)
    }

    class JsonD(
        var a: Int,
        @Since(1.1) var name: String?, // 当前version必须小 >=1.1 才能解析
        @Until(0.9) var matrix: String? // 当前version必须小 <0.9 才能解析
    ) {

        override fun toString(): String {
            return "JsonD(a=$a, name=$name, matrix=$matrix)"
        }
    }

    /**
     * 测试Gson序列化中常用的注解
     * GsonBuilder().setVersion(1.1).create() 必须setVersion()才能生效，并且 ！= -1.0
     * Since 当前Gson的 version必须小 >= 设置的version 才生效
     * Until 当前Gson的 version必须小 < 设置的version 才生效
     */
    fun test4() {
        val b = JsonD(2, "泡利", "AB")

        val gson = GsonBuilder().setVersion(1.1).create()
        val toJson = gson.toJson(b)
        println(toJson)

        val fromJson = gson.fromJson(toJson, JsonD::class.java)
        println(fromJson)
    }

    class JsonE(
        @JsonAdapter(MyTimestampAdapter::class) var timeStamp: Long,
        @JsonAdapter(MySpeedAdapter::class) @SerializedName("hideSpeed") var speed: Int
    ) {

        override fun toString(): String {
            return "JsonE(timeStamp=$timeStamp, speed=$speed)"
        }
    }

    class MyTimestampAdapter : TypeAdapter<Long>() {
        override fun write(out: JsonWriter?, value: Long?) {
            out?.run {
                beginObject()
                name("timeStamp")
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val format = simpleDateFormat.format(value)
                value(format)
                endObject()
            }

        }

        override fun read(`in`: JsonReader?): Long {
            var l = 0L
            `in`?.run {
                beginObject()
                nextName()
                val nextString = nextString()
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val date = simpleDateFormat.parse(nextString)
                l = date!!.time
                endObject()
            }
            return l
        }
    }

    class MySpeedAdapter : TypeAdapter<Int>() {
        override fun write(out: JsonWriter?, value: Int?) {
            out?.value("${value}km/s")
        }

        override fun read(`in`: JsonReader?): Int {
            var i = 0
            val nextString = `in`?.nextString()
            val pattern = "^\\d+"
            val p = Pattern.compile(pattern)
            val matcher = p.matcher(nextString!!)
            if (matcher.find())
                i = matcher.group().toInt()
            return i
        }
    }

    /**
     * 测试Gson序列化中常用的注解
     * JsonAdapter 可以作用的Class、Field上，用来做一些特殊字段的转化
     */
    fun test5() {
        val currentTimeMillis = System.currentTimeMillis()
        println("解析前的数据${currentTimeMillis}")
        val b = JsonE(currentTimeMillis, 22)

        val gson = GsonBuilder().create()
        val toJson = gson.toJson(b)
        println(toJson)

        val fromJson = gson.fromJson(toJson, JsonE::class.java)
        println(fromJson)

        /*
        解析前的数据1601272652734
        {"timeStamp":{"timeStamp":"2020-09-28 01:57:32"},"speed":"22km/s"}
        JsonE(timeStamp=1601229452000, speed=22)
         */
    }

    data class StarA(var index: Int, var name: String)
    data class Galaxy(var stars: List<StarA>, var name: String)


    class MyStringAdapter : TypeAdapter<String?>() {
        override fun write(out: JsonWriter?, value: String?) {
            out?.value(value)
        }

        override fun read(`in`: JsonReader?): String? {
            `in`?.run {
                val peek = peek()
                if (peek == JsonToken.STRING) {
                    val nextString = nextString()
                    println(nextString)
                    return if (null == nextString || "" == nextString) null else nextString
                }
                if (peek == JsonToken.BEGIN_OBJECT) {
                    val nextString = nextString()
                    println(nextString)
                    return if (null == nextString || "" == nextString) null else nextString
                }
            }
            return null
        }
    }


    fun test6() {
        val myGson = GsonBuilder()
            .registerTypeAdapter(String::class.java, MyStringAdapter())
            .create()

        val galaxy = Galaxy(
            listOf(StarA(3, "蓝星-地球"), StarA(2, "长庚星-金星")),
            "太阳系"
        )

//        val toJson = myGson.toJson(galaxy)
//        println(toJson)
//        val fromJson = myGson.fromJson(toJson, Galaxy::class.java)
//        println(fromJson)

//        val sunGalaxy = "{\"stars\":[{\"index\":3,\"name\":\"蓝星-地球\"}],\"name\":\"太阳系\"}"
        // 模拟服务器 定义的array字段，返回了""
        val sunGalaxy = "{\"stars\":\"\",\"name\":\"太阳系\"}"
        val fromJson2 = myGson.fromJson(sunGalaxy, Galaxy::class.java)
        println(fromJson2)
    }


}