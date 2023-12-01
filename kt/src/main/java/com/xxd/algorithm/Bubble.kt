package com.xxd.algorithm

/**
 *    author : xxd
 *    date   : 2023/11/26
 *    desc   : 冒泡排序，交换排序的一种
 */


fun main() {
    val array = intArrayOf(42, 13, 34, 7, 11, 24, 63, 32)
    bubbleSort(array)
    println(array.toList())
}

/**
 * 2层循环都与n有关，时间复杂度：n²
 * 空间复杂度：1
 * >才交换，稳定
 */
fun bubbleSort(array: IntArray) {
    for (i in array.indices) {
        for (j in 0 until array.size - i - 1) {
            if (array[j] > array[j + 1])
                swap(array, j, j + 1)
        }
    }
}

private fun swap(array: IntArray, i: Int, j: Int) {
    if (i == j)
        return
    val temp = array[i]
    array[i] = array[j]
    array[j] = temp
}
