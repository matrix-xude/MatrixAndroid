package com.xxd.kt.generic

import java.util.function.Function

/**
 *    author : xxd
 *    date   : 2026/3/14
 *    desc   : in out 用法示例, 声明处型变
 */

abstract class InOutDemo<in T , out K> {

    // 1. 用在变量上， out 的不能 set(),因为单做参数用了，所以没有实际意义
    /*var k : K
        get() {
            TODO()
        }
        set(value) {
            k = value
        }*/

    // 2. 用在变量上， in 的不能 get(),因为单做返回值用了，所以没有实际意义
    /*var t : T
        get() {
            TODO()
        }
        set(value) {
            t = value
        }*/

    // 3. 泛型一般用在abstract方法上（因为用在实际方法上作用不大，泛型擦除导致不能 判断、实例化）
    abstract fun m1(t: T): K

    // 4. 用在真实方法上，作用不大，泛型擦除导致不能 判断、实例化
    fun m2(t: T): K {
        // 没法实例化，实体方法没用
        // return K()

        // 这样没问题，但是还是借助了子类的抽象实现
        return m1(t)
    }

    // ————————————————————————————————————————下面是泛型嵌套的使用—————————————————————————————————————————————————————————————

    // 支持,T 最终是被 m3 内部逻辑消费掉的。这完全符合 T 作为 in 消费者的身份。
    fun m3(src: List<T>) {
    }

    // 报错,这里的 T 发生了“型变溢出”。你试图把一个只能作为“输入”的类型 T，放进了一个会把它作为“输出”暴露出来的容器 MutableList 中。
//    fun m4(src : MutableList<T>) {
//    }

    // 报错,本质矛盾： 拿到这个 K 后，你就相当于在方法内部消费了 K。而你定义的 K 是 out（只能被生产，不能被消费）。
//    fun m5(src : List<K>) {
//    }

    // 这里能通过，因为List<out E>只能返回E ，而TestInClass<in E> 只能放入E
    fun m6(src: TestInClass<K>) {
        // 从外部获取到K，是返回值，就是out K
        val k: K = m7()
        src.put(k)
    }

    abstract fun m7(): K

    // 测试 out K 的型变
    class TestInClass<in E> {
        fun put(e: E) {
        }
    }

    // 总结如下
    val s =
        """
            在 Kotlin 编译器眼中，泛型的位置是会叠加的。
                参数的位置本身是 in 位置。
                List 的泛型位是 out 位置。
            这里有一个像正负号相乘一样的逻辑：
                in (参数位) * out (List位) = in 效果。
                    所以 in T 放在这里是合法的（负正得负）。
                in (参数位) * in (某个逆变容器位) = out 效果。
                    所以 out K 理论上可以放在一个逆变容器的参数位里（负负得正，这叫 Contravariant Position）。
        """.trimIndent()

}

fun main() {
    // 1. 这是java的Function ， 就是 <T,R>， 不支持形变
    var f1: Function<Number, Number> = object : Function<Number, Number> {
        override fun apply(p0: Number): Number {
            return p0
        }
    }

    // 因为不支持形变，所以这里会报错
//    f1 = object : Function<Any, Int> {
//        override fun apply(p0: Any): Int {
//            return 1
//        }
//    }


    // 2. 这是kotlin的Function ， 就是 <in T,out R>， 支持形变
    // (tips : f3 必须显示指定泛型，如果不用：将其视为一个特定的匿名子类型，而不是通用的接口类型，导致它失去了型变的灵活性)
    var f3: kotlin.Function1<Number, Number> = object : kotlin.Function1<Number, Number> {
        override fun invoke(p1: Number): Number {
            return p1
        }
    }

    f3 = object : kotlin.Function1<Any, Int> {

        override fun invoke(p1: Any): Int {
            return 1
        }
    }

}