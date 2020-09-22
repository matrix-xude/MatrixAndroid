package com.xxd.thread.encode

import java.io.*

/**
 * author : xxd
 * date   : 2020/9/21
 * desc   : 调研 Serializable 序列化深入的内容
 *          结果：序列化后的内容包括了类的包名，反序列化必须使用同样的包名，多字段少字段无所谓，但是其他包名均不行
 *               同时必须赋值serialVersionUID否则，每次编译后version版本不同异常
 */
class EncodeDeep {

    companion object {
//        const val DEEP_OUT_PATH = "deep.out"
        const val DEEP_OUT_PATH = "deep2.out"

        // https://blog.csdn.net/fuhao_ma/article/details/102969349
    }

    data class Deep(var index: Int, var name: String, var stars: String) : CopyDeep(),
        Serializable {
        companion object {
            const val serialVersionUID = 1L
        }
    }

    /**
     * 父类不实现 Serializable 不会被序列化，也不会报错
     */
    open class CopyDeep  {
        var copyNum: Int? = null
        var copyName: String? = null

        companion object {
            const val serialVersionUID = 2L
        }
    }

    /**
     * 测试没有无餐构造函数是否能被反序列化
     * 结论：反序列化不需要无参构造函数，可以反序列化成功
     * tip：java中有4种创建类的方法 1.new 2.反射 3.clone 4.ObjectInputStream的readObject（）方法
     */
    class DeepConstructor private constructor(var a : Int, var name : String, var b : Int) : Serializable{

        companion object {
            const val serialVersionUID = 1L
        }

        override fun toString(): String {
            return "DeepConstructor(a=$a, name='$name')"
        }

    }

    fun encodeFile() {
        val deep = Deep(2, "方法", "water star")
        deep.copyNum = 5
        deep.copyName = "rrr"
//        val deep = DeepConstructor(3,"量子")
        try {
            val fos = FileOutputStream(DEEP_OUT_PATH)
            val oos = ObjectOutputStream(fos)
            oos.writeObject(deep)
        } catch (e: Exception) {
            throw e
        }
    }

    fun decodeFile() {
        try {
            val fis = FileInputStream(DEEP_OUT_PATH)
            val ois = ObjectInputStream(fis)
            val deep = ois.readObject() as Deep
            println(deep)
        } catch (e: Exception) {
            throw e
        }
    }


}