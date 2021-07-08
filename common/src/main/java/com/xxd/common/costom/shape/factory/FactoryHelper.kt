package com.xxd.common.costom.shape.factory

import android.graphics.Color

/**
 * author : xxd
 * date   : 2021/6/14
 * desc   :
 */
class FactoryHelper private constructor() {

    companion object {
        const val DEFAULT_RADIUS = 0F
        const val DEFAULT_COLOR = Color.TRANSPARENT
        const val DEFAULT_STROKE_WIDTH = 0
        const val DEFAULT_STROKE_COLOR = Color.TRANSPARENT
        const val DEFAULT_TRANSPARENCY_SCALE = 1F // 默认的透明度变化比例

        /**
         * 计算具体的半径
         * @param radius 保底的半径
         * @param boundaryRadius 每个边上具体的半径
         */
        fun calculateRadius(radius: Float, boundaryRadius: Float): Float {
            return if (boundaryRadius == DEFAULT_RADIUS) radius else boundaryRadius
        }
    }
}