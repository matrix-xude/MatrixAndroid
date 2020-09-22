package com.xxd.thread.encode

import com.xxd.common.util.log.LogUtil
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

/**
 * author : xxd
 * date   : 2020/9/21
 * desc   : 调研 Serializable 序列化深入的内容
 *          结果：序列化后的内容包括了类的包名，反序列化必须使用同样的包名，多字段少字段无所谓，但是其他包名均不行
 *               同时必须赋值serialVersionUID否则，每次编译后version版本不同异常
 */
class EncodeDeep {

    companion object {
        const val DEEP_OUT_PATH = "deep.out"

        // https://blog.csdn.net/fuhao_ma/article/details/102969349
    }

    data class Deep(var index: Int, var name: String) : Serializable {
        companion object {
            const val serialVersionUID = 1L
        }
    }

    data class CopyDeep(var index: Int, var name: String) : Serializable {
        companion object {
            const val serialVersionUID = 1L
        }
    }

    fun encodeFile() {
        val deep = Deep(1, "fff")
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