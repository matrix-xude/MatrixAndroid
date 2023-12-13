package com.xxd.thread.encode

import java.io.*

/**
 *    author : xxd
 *    date   : 2020/9/21
 *    desc   : 序列化的测试类，放到了测试类中进行测试
 */
class EncodeTest {

    // 星系
    class Galaxy : Serializable {

        companion object {
            const val serialVersionUID = 1L
        }

        var name: String? = null
        var diameter: String? = null

        @Transient
        var fixedStar: FixedStar? = null
        var planet: Planet? = null


        override fun toString(): String {
            return "Galaxy(name=$name, diameter=$diameter, fixedStar=$fixedStar, planet=$planet)"
        }

    }

    // 恒星
    class FixedStar : Serializable {
        companion object {
            const val serialVersionUID = 1L
        }

        var name: String? = null
        var lifecycle: Int? = null
        var burst: String? = null

        override fun toString(): String {
            return "FixedStar(name=$name, lifecycle=$lifecycle 亿年, burst=$burst)"
        }

        // 以下4个是通过反射调用的方法，用来在序列化的时候做自己的处理
        private fun readObject(ois: ObjectInputStream) {
            println("readObject")
            name = ois.readObject() as String
            lifecycle = ois.readInt()
            burst = ois.readObject() as String
        }

        private fun writeObject(oos: ObjectOutputStream) {
            println("writeObject")
            oos.writeObject(name)
            oos.writeInt(lifecycle!!)
            oos.writeObject(burst)
        }

        private fun readResolve(): Any {
            println("readResolve")
            return FixedStar().apply {
                name = "${this@FixedStar.name}_readResolve"
                lifecycle = this@FixedStar.lifecycle!! + 3
                burst = "${this@FixedStar.burst}_readResolve"
            }
        }

        private fun writeReplace(): Any {
            println("writeReplace")
            return FixedStar().apply {
                name = "${this@FixedStar.name}_writeReplace"
                lifecycle = this@FixedStar.lifecycle
                burst = "${this@FixedStar.burst}_writeReplace"
            }
        }
    }

    // 行星
    data class Planet(val name: String, var speed: Double) : Serializable {
        companion object {
            const val serialVersionUID = 1L
        }

        override fun toString(): String {
            return "Planet(name='$name', speed=${speed}km/s)"
        }
    }

    /**
     * 测试多引用写入
     * 什么是多次写入，同一个对象多次写入，如果改变了值，不会立马改变
     *
     *
     *  Planet(name='太白星', speed=22.4km/s)
     *  Planet(name='太白星', speed=22.4km/s) // 第二次修改速度后没变
     *  true  // data类型的equals方法与普通类不同，只比较数值，调试可以看到2个类是不同的类
     */
    fun test1() {
        val planet = Planet("太白星", 22.4)
        try {


            val bos = ByteArrayOutputStream()
            val oos = ObjectOutputStream(bos)
            oos.writeObject(planet)
            planet.speed = 43.3 // 改变了数值

            /**
             * 解决多引用写入的方法1
             */
            fun resolver1() {
                oos.reset()
            }

            /**
             * 解决多引用写入的方法2
             */
            fun resolver2() {
                oos.writeUnshared(planet)
            }

            resolver1()
            oos.writeObject(planet)
            val byteArray = bos.toByteArray()

            val bis = ByteArrayInputStream(byteArray)
            val ois = ObjectInputStream(bis)
            val planet1 = ois.readObject() as Planet
            val planet2 = ois.readObject() as Planet
            println(planet1)
            println(planet2)
            println(planet1 === planet2)


        } catch (e: Exception) {
            throw e
        }

    }

    /**
     * 测试防止序列化的字段
     * @Transient 通过标记可以防止此字段被序列化
     */
    fun test2() {
        val galaxy = Galaxy().apply {
            name = "人马座α星系"
            diameter = "12亿光年"
            fixedStar = FixedStar().apply {
                name = "大熊星"
                lifecycle = 35
            }
            planet = Planet("仙女星", 357.22)
        }
        val byteArray = writeObject(galaxy)
        val readObject = readObject<Galaxy>(byteArray)
        println(readObject)
    }

    /**
     * 测试Serializable 的4个反射方法
        writeReplace
        writeObject
        readObject
        readResolve
        FixedStar(name=北极星_writeReplace_readResolve, lifecycle=25 亿年,
        burst=永不爆发_writeReplace_readResolve)
     */
    fun test3() {
        val fixedStar = FixedStar().apply {
            name = "北极星"
            lifecycle = 22
            burst = "永不爆发"
        }
        val byteArray = writeObject(fixedStar)
        val readObject = readObject<FixedStar>(byteArray)
        println(readObject)
    }

    /**
     * 测试序列化前和序列化后对象是否为同一个
     * 不是同一个
     */
    fun test4() {
        val planet = Planet("天王星", 32.67)
        val byteArray = writeObject(planet)
        val planetAfter = readObject<Planet>(byteArray)
        println(planet === planetAfter)  // kotlin中===才是判断对象地址，==是判断对象的equals
    }

    private fun writeObject(any: Any): ByteArray {
        try { // AutoCloseable在try中可以自动关闭io流
            val bos = ByteArrayOutputStream()
            val oos = ObjectOutputStream(bos)
            oos.writeObject(any)
            return bos.toByteArray()
        } catch (e: Exception) {
            throw e
        }
    }

    private fun <T> readObject(byteArray: ByteArray): T {
        try {
            val bis = ByteArrayInputStream(byteArray)
            val ois = ObjectInputStream(bis)
            return ois.readObject() as T
        } catch (e: Exception) {
            throw e
        }
    }

}