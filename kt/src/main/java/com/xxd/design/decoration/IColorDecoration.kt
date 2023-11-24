package com.xxd.design.decoration

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */
abstract class IColorDecoration constructor(private val color: IColor) : IColor {

    override fun showColor() {
        color.showColor()
    }
}