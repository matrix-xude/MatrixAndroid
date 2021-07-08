package com.xxd.common.costom.shape

import com.xxd.common.costom.shape.factory.DrawableFactory
import com.xxd.common.costom.shape.factory.model.JudgeDrawableFactory

/**
 * author : xxd
 * date   : 2021/6/14
 * desc   : 控制器，可以获取到对应的工厂
 */
class ShapeController private constructor() {

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ShapeController()
        }
    }

    /**
     * 获取对应的工厂
     */
    fun provideDrawableFactory(): DrawableFactory {
        return JudgeDrawableFactory.instance
    }
}