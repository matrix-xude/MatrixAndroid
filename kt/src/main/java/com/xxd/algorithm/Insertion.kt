package com.xxd.algorithm

/**
 *    author : xxd
 *    date   : 2023/11/26
 *    desc   : 插入排序
 */

fun main() {
    val array = intArrayOf(42, 13, 34, 7, 11, 24, 63, 32)
    insertionSort2(array)
    println(array.toList())
}

/**
 * 两层for循环，都与n相关，所以时间复杂度：n²
 * 临时空间只有temp,常熟，所以空间复杂度：1
 * 可以用》=比较插入，稳定性：稳定
 */
fun insertionSort(array: IntArray) {
    for (i in array.indices) {
        val temp = array[i]  // 当前需要排序的数
        for (j in i downTo 0) {
            if (j == 0) { // 没有前一个数了,将目标数放入第一个
                array[0] = temp
            } else {  // 比较前一个数和目标数的大小
                if (array[j - 1] <= temp) {
                    array[j] = temp
                    break // 前面的数都比目标数小了
                } else {
                    array[j] = array[j - 1]
                    continue  // 将前一个数插入到当前循环数中
                }
            }
        }
    }
}

/**
 * 第二层循环用while更加清晰
 */
fun insertionSort2(array: IntArray) {
    for (i in array.indices) {
        var preIndex = i - 1 // 前一个数index
        val temp = array[i]  // 需要排序的数
        while (preIndex >= 0 && array[preIndex] > temp) {
            array[preIndex + 1] = array[preIndex]
            preIndex--
        }
        array[preIndex + 1] = temp // 找当排序好的当前位置，插入temp值
    }
}