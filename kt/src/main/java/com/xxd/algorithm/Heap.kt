package com.xxd.algorithm

/**
 *    author : xxd
 *    date   : 2023/11/26
 *    desc   : 堆排序，也是选择排序的一种，从跟节点和2个子节点中选择最大的一个，然后交换
 */

fun main() {
    val array = intArrayOf()
    heapSort(array)
    println(array.toList())
}

private var len = 0 // 堆需要调整的长度

/**
 * 建立堆时，外循环是n，内循环是 log n,所以外部是 n log n
 * 调整堆时，也是 n log n,加起来时间复杂度还是 n log n
 * 空间复杂度：1，与快速排序不同，这里递归调用时在for循环中，永远是常熟
 * 稳定性：很显然不稳定
 */
fun heapSort(array: IntArray) {
    buildMaxHeap(array)
    for (i in array.size - 1 downTo 0) {
        swap(array, 0, i)
        len--
        heapify(array, 0)
    }
}

// 创建大堆，只需要执行1次
private fun buildMaxHeap(array: IntArray) {
    len = array.size
    for (i in array.size / 2 downTo 0) {
        heapify(array, i)
    }
}

/**
 * 将堆中的某个数调整到合适的位置
 * @param len 需要调整的最大数
 */
private fun heapify(array: IntArray, i: Int) {
    val left = i * 2 + 1
    val right = i * 2 + 2
    var maxIndex = i

    if (left < len && array[left] > array[maxIndex])
        maxIndex = left
    if (right < len && array[right] > array[maxIndex])
        maxIndex = right

    if (maxIndex != i) {  // 需要调整
        swap(array, i, maxIndex)
        heapify(array, maxIndex) //递归往下调整
    }
}

private fun swap(array: IntArray, i: Int, j: Int) {
    if (i == j)
        return
    val temp = array[i]
    array[i] = array[j]
    array[j] = temp
}
