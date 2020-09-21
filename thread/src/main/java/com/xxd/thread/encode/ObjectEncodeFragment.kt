package com.xxd.thread.encode

import com.xxd.common.base.BaseFragment
import com.xxd.common.util.log.LogUtil
import com.xxd.thread.R
import kotlinx.android.synthetic.main.thread_fragment_encode_object.*
import java.io.*
import kotlin.jvm.Throws

/**
 * author : xxd
 * date   : 2020/9/20
 * desc   : 对象序列化
 */
class ObjectEncodeFragment : BaseFragment() {

    companion object {
        const val OUT_PATH = "book.out"
    }

    private val myBook = Book().apply {
        name = "星空的琴弦"
        price = 28.88
        store = Store().apply {
            protagonist = "牛顿"
            tale = "伦敦大瘟疫，光的散射，微积分……"
        }
    }

    private var byteArray: ByteArray? = null

    override fun getLayoutId(): Int {
        return R.layout.thread_fragment_encode_object
    }

    override fun initView() {
        super.initView()
        tv1.setOnClickListener {
            encodeBook()
        }
        tv2.setOnClickListener {
            decodeBook()
        }
        tv3.setOnClickListener {
            encodeFile()
        }
        tv4.setOnClickListener {
            decodeFile()
        }
    }

    @Throws(IOException::class)
    private fun encodeBook() {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(myBook)
        byteArray = baos.toByteArray()
        oos.close()
        baos.close()
    }

    private fun decodeBook() {
        val bais = ByteArrayInputStream(byteArray)
        val ois = ObjectInputStream(bais)
        val book = ois.readObject()
        ois.close()
        bais.close()
        LogUtil.d(book)
    }

    fun encodeFile() {
        val fos = FileOutputStream(OUT_PATH)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(myBook)
        oos.close()
        fos.close()
    }

    fun decodeFile() {
        val fis = FileInputStream(OUT_PATH)
        val ois = ObjectInputStream(fis)
        val book = ois.readObject()
        ois.close()
        fis.close()
        LogUtil.d(book)
        println(book)
    }

    /**
     * 必须实现 Serializable 才能序列化
     */
    class Book : Serializable {

        companion object {
            const val serialVersionUID = 1L
        }

        var name: String? = null
        var price: Double? = null

        var store: Store? = null
        override fun toString(): String {
            return "Book(name=$name, price=$price, store=$store)"
        }

    }

    /**
     * 子类也必须实现 Serializable 才能序列化
     */
    class Store : Serializable {
        companion object {
            const val serialVersionUID = 1L
        }

        var protagonist: String? = null
        var tale: String? = null
        var college: String? = null
        override fun toString(): String {
            return "Store(protagonist=$protagonist, tale=$tale, college=$college)"
        }


//        override fun toString(): String {
//            return "Store(protagonist='$protagonist', tale='$tale')"
//        }

    }
}