package com.xxd.design.factoryMethod

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   : 抽象工厂，约定生产的产品
 */
interface IFactory {
    fun createGun(): IGun
}