package com.xxd.design.factoryMethod

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */

fun main() {
    PistolFactory.createGun().shoot()
    RifleFactory.createGun().shoot()
}