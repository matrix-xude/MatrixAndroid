package com.xxd.algorithm

/**
 *    author : xxd
 *    date   : 2023/11/26
 *    desc   : 希尔排序，是对插入排序的一种改进。
 *    因为插入排序移动的步数与原数据的排序混乱度有关，原数据越接近排序完成，移动的次数越少
 *    所有希尔排序按gap进行分组
 */
fun main() {
    // 分组执行
    val array = intArrayOf(42, 13, 34, 7, 11, 24, 63, 32)
    shellSort(array)
    println(array.toList())

    // 交替执行，与分组执行对比，虽然少了一个for循环，但是时间复杂度一模一样
    val array2 = intArrayOf(42, 13, 34, 7, 11, 24, 63, 32)
    shellSort2(array2)
    println(array2.toList())
}

/**
 * 希尔排序的外部是一种 log n的时间复杂度
 * 空间复杂度：1
 * 因为是交替排序，所以不稳定
 */
fun shellSort(array: IntArray) {
    var gap = array.size
    while (gap > 1) {  // 间隔每次/2，直到=1为止
        gap /= 2
        shell(array, gap)
    }
}

fun shellSort2(array: IntArray) {
    var gap = array.size
    while (gap > 1) {  // 间隔每次/2，直到=1为止
        gap /= 2
        shell2(array, gap)
    }
}

/**
 * 这种分组的思路清晰，按照分组去排序
 * 但是这种方法等于是多了一个for循环，内部的时间复杂度不好计算：可能是n
 */
private fun shell(array: IntArray, gap: Int) {
    var time = 0
    // 外循环表示分组数
    for (i in 0 until gap) {
        // 每一组内的数据，做普通插入排序即可
        for (j in i until array.size step gap) {
            var preIndex = j - gap
            val temp = array[j]
            while (preIndex >= 0 && array[preIndex] > temp) {
                array[preIndex + gap] = array[preIndex]
                time++ // 次数统计，与算法无关
                preIndex -= gap
            }
            array[preIndex + gap] = temp // 放入当前temp数据
            time++
        }
    }
    println("分组再插入排序交换次数：$time")
}

/**
 * 思路上是分组，但是实际上代码是分组交替执行，一次for循环解决问题
 * 时间复杂度
 */
private fun shell2(array: IntArray, gap: Int) {
    var time = 0
    for (i in array.indices) {
        // 交替执行分组中的每一组插入排序
        var preIndex = i - gap
        val temp = array[i]
        while (preIndex >= 0 && array[preIndex] > temp) {
            array[preIndex + gap] = array[preIndex]
            time++
            preIndex -= gap
        }
        array[preIndex + gap] = temp
        time++
    }
    println("交替插入排序交换次数：$time")
}
