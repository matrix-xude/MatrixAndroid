package com.xxd.compose.data

/**
 *    author : xxd
 *    date   : 2024/1/30
 *    desc   : fake news !
 */
object FakeData {

    private val simpleStr = listOf(
        "量子力学是一门简单的微观科学",
        "彭齐亚斯、威尔逊发现了微波背景辐射",
        "托勒密创建了一个套以地球为中心的天体运动计算方法，并统治了天文学1000多年",
        "雷达的反射波强弱与物体的面积大小无关，而与表面积的形状有关",
        "when the YMCA rings, Donald Trump comes out!"
    )

    private val shotStrList = listOf("黑体辐射", "氢原子的光谱", "贝尔不等式", "脉冲星", "自旋1/2", "Trump Win", "泡利不相容")

    /**
     * 造一个简单的语句
     */
    fun fakeSimpleString(): String {
        val random = (simpleStr.indices).random()
        return simpleStr[random]
    }

    /**
     * 造一个短语集合
     * @param size 需要的短语数量，超过最大值返回最大的集合
     */
    fun fakeShotStrList(size: Int): List<String> {
        val realSize = when {
            size < 0 -> 0
            size >= shotStrList.size -> shotStrList.size
            else -> size
        }
        return shotStrList.subList(0, realSize)
    }
}