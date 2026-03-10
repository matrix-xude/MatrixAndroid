package com.xxd.kt.generic

import com.xxd.kt.generic.domain.Animal
import com.xxd.kt.generic.domain.Dog

/**
 *    author : xxd
 *    date   : 2026/3/10
 *    desc   :  声明处形变2 ： 逆变 Contravariance
 */
// 在声明处加了 in，代表 T 只能出现在“输入”位置
interface Consumer<in T> {
    fun consume(item: T)
    // fun produce(): T // 报错！编译器不允许 T 作为返回值
}

fun main() {
    val animalConsumer: Consumer<Animal> = object : Consumer<Animal> {
        override fun consume(item: Animal) {
            println("consume animal")
        }
    }


    // 意义体现：原本父类泛型赋值给子类泛型是不允许的
    // 但有了 in，这变得合法且安全
    val dogConsumer: Consumer<Dog> = animalConsumer

    dogConsumer.consume(Dog()) // 安全，因为 animalConsumer 本就能处理任何 Animal
}