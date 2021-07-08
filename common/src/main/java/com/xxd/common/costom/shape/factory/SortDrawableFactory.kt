package com.xxd.common.costom.shape.factory

/**
 * author : xxd
 * date   : 2021/6/14
 * desc   : 因为所有 AttributeSet 涉及到排序问题，抽取出来
 */
abstract class SortDrawableFactory : DrawableFactory {

    // 排序之后的数组
    protected val sortedArray: IntArray

    // 排序之后的数组对应index
    protected val sortedMap: Map<Int, Int>

    init {
        val sorted = getUnSortList().sorted()
        sortedArray = sorted.toIntArray()
        sortedMap = HashMap()
        sorted.forEachIndexed { index, id ->
            sortedMap[id] = index
        }
    }

    /**
     * 获取需要排序的属性集合
     */
    abstract fun getUnSortList(): List<Int>
}