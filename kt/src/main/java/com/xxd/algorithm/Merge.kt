package com.xxd.algorithm

/**
 *    author : xxd
 *    date   : 2023/11/26
 *    desc   : 归并排序，是把2个有序数列，继续合并排序
 */

fun main() {
    val array = intArrayOf(42, 13, 34, 7, 11, 24, 63, 32)
    val newArray = mergeSort(array)
    println("array=${array.toList()}")
    println("newArray=${newArray.toList()}")

}

fun mergeSort(array: IntArray): IntArray {
    if (array.size <= 1)
        return array
    val divider = array.size / 2
    return merge(mergeSort(array.sliceArray(0..<divider)), mergeSort(array.sliceArray(divider..<array.size)))
}

// 将2个有序数组合并
private fun merge(leftArray: IntArray, rightArray: IntArray): IntArray {

    // 2个数组当前指示坐标
    var leftIndex = 0
    var rightIndex = 0

    // 合并后的数组
    val mergeArray = IntArray(leftArray.size + rightArray.size)
    for (i in mergeArray.indices) {
        when {
            leftIndex >= leftArray.size -> {  // 左数组数据已经取完，直接使用右数组数字
                mergeArray[i] = rightArray[rightIndex++]
            }

            rightIndex >= rightArray.size -> {  // 右数组数据已经取完，直接使用数组列数字
                mergeArray[i] = leftArray[leftIndex++]
            }

            else -> {  // 两边数组都未取完
                val left = leftArray[leftIndex]
                val right = rightArray[rightIndex]
                if (left <= right) {
                    leftIndex++
                    mergeArray[i] = left
                } else {
                    rightIndex++
                    mergeArray[i] = right
                }
            }
        }
    }

    return mergeArray
}