package com.xxd.design.builder

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   : 梦想创造者
 */
class Dream private constructor(target: Int, struggle: Float) {

    var target: Int  // 目标
    var struggle: Float   // 努力程度

    init {
        this.target = target
        this.struggle = struggle
    }

    // 在kotLin中，很大程度把set函数给隐藏了，外部可以直接调用
    class Builder() {
        var target: Int = 0
        var struggle: Float = 0f  // 努力程度

        fun build(): Dream {
            return Dream(target, struggle)
        }
    }

}