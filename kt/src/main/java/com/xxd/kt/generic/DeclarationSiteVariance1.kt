package com.xxd.kt.generic

import com.xxd.kt.generic.domain.Animal
import com.xxd.kt.generic.domain.Dog

/**
 *    author : xxd
 *    date   : 2026/3/10
 *    desc   : 声明处形变1 ： 协变 Covariance
 */
// 在声明处加了 out，代表 T 只能出现在“输出”位置
interface Producer<out T> {
    fun produce(): T
    // fun consume(item: T) // 报错！编译器不允许 T 作为参数输入
}

fun main() {
    val dogProducer: Producer<Dog> = object : Producer<Dog> {
        override fun produce(): Dog {
            return Dog()
        }
    }

    // 意义体现：不需要写 Producer<? extends Animal>
    // 直接赋值，像普通类型一样自然
    val animalProducer: Producer<Animal> = dogProducer

    val animal = animalProducer.produce() // 拿到的一定是 Animal 或其子类
}