package com.xxd.design.abstractFactory

import com.xxd.design.factoryMethod.IGun
import com.xxd.design.factoryMethod.Pistol

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   : 特斯拉造手枪
 */
object TeslaFactory : AbstractFactory {
    override fun createGun(): IGun {
        return Pistol()
    }

    override fun createCar(): ICar {
        return Tesla()
    }
}