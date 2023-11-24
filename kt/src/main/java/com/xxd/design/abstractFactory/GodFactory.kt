package com.xxd.design.abstractFactory

import com.xxd.design.factoryMethod.IGun
import com.xxd.design.factoryMethod.Rifle

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */
object GodFactory : AbstractFactory {
    override fun createGun(): IGun {
        return Rifle()
    }

    override fun createCar(): ICar {
       return WulingHongGuang()
    }
}