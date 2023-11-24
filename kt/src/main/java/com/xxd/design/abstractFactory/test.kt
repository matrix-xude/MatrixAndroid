package com.xxd.design.abstractFactory

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */

fun main() {
    TeslaFactory.run {
        createCar().run()
        createGun().shoot()
    }

    GodFactory.run {
        createCar().run()
        createGun().shoot()
    }
}