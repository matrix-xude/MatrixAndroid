package com.xxd.design.abstractFactory

import com.xxd.design.factoryMethod.IGun

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   : 抽象工厂与工厂方法差不多，就是工厂生产的是一簇产品，这里借用了工厂方法的一些产品
 */
interface AbstractFactory {

    fun createGun(): IGun
    fun createCar(): ICar
}