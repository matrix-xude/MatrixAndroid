package com.xxd.algorithm

/**
 *    author : xxd
 *    date   : 2023/11/26
 *    desc   : 简单选择排序
 */

fun main() {
    val array = intArrayOf(42, 13, 34, 7, 11, 24, 63, 32)
    selectionSort(array)
    println(array.toList())
}

/**
 * 时间复杂度：n²
 * 空间复杂度：1
 * 稳定性：不稳定，如（5,2,5,1,8）,第一个5与1交换后，就不稳定了
 */
fun selectionSort(array: IntArray) {
    for (i in array.indices) {
        var minIndex = i
        for (j in i until array.size) {
            if (array[j] < array[minIndex])
                minIndex = j
        }
        swap(array, i, minIndex)
    }
}

private fun swap(array: IntArray, i: Int, j: Int) {
    if (i == j)
        return
    val temp = array[i]
    array[i] = array[j]
    array[j] = temp
}