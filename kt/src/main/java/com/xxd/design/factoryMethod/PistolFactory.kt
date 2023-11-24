package com.xxd.design.factoryMethod

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */
object PistolFactory : IFactory {
    override fun createGun(): IGun {
        return Pistol()
    }
}