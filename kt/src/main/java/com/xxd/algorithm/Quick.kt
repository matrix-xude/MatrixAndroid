package com.xxd.algorithm

/**
 *    author : xxd
 *    date   : 2023/11/26
 *    desc   : 快速排序，交换排序的一种，需要找基准pivot
 */
fun main() {
    val array = intArrayOf(42, 13, 34, 7, 11, 24, 63, 32)
    quickSort(array)
    println(array.toList())
}

/**
 * 快速排序折半递归 n log n
 * 空间1
 * 稳定性：因为交换颠倒了顺序，不稳定
 */
fun quickSort(array: IntArray) {
    quick(array, 0, array.size)
}

/**
 * 快速排序找到基准后需要递归
 * 包含startIndex，不包含endIndex
 */
private fun quick(array: IntArray, startIndex: Int, endIndex: Int) {
    if (endIndex - startIndex <= 1)  // 结束递归的条件，只剩1个数以内不需要再排序
        return
    val pivot = array[startIndex]  // 基准
    var lessNum = 0 // 小于基准的数个数

    for (i in startIndex + 1 until endIndex) {
        if (array[i] < pivot) {
            // 记录小于基准数个数，并交换该数到基准数后
            lessNum++
            swap(array, i, startIndex + lessNum)
        }
    }
    // 最后将基准数交换到lessNum的最后一个
    swap(array, startIndex, startIndex + lessNum)
    // 递归调佣自己
    quick(array, startIndex, startIndex + lessNum)
    quick(array, startIndex + lessNum + 1, endIndex)
}

private fun swap(array: IntArray, i: Int, j: Int) {
    if (i == j)
        return
    val temp = array[i]
    array[i] = array[j]
    array[j] = temp
}