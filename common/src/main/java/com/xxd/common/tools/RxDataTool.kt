package com.xxd.common.tools

import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import android.util.SparseLongArray
import java.lang.reflect.Array

/**
 *    author : xxd
 *    date   : 2021/8/26
 *    desc   : 处理数据
 *
 * ┌───┐   ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│   │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│  ┌┐    ┌┐    ┌┐
 * └───┘   └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘  └┘    └┘    └┘
 * ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐ ┌───┬───┬───┐ ┌───┬───┬───┬───┐
 * │~ `│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp │ │Ins│Hom│PUp│ │N L│ / │ * │ - │
 * ├───┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤ ├───┼───┼───┤ ├───┼───┼───┼───┤
 * │ Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ │ │Del│End│PDn│ │ 7 │ 8 │ 9 │   │
 * ├─────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤ └───┴───┴───┘ ├───┼───┼───┤ + │
 * │ Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │               │ 4 │ 5 │ 6 │   │
 * ├──────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤     ┌───┐     ├───┼───┼───┼───┤
 * │ Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │     │ ↑ │     │ 1 │ 2 │ 3 │   │
 * ├─────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤ ┌───┼───┼───┐ ├───┴───┼───┤ E││
 * │ Ctrl│    │Alt │         Space         │ Alt│    │    │Ctrl│ │ ← │ ↓ │ → │ │   0   │ . │←─┘│
 * └─────┴────┴────┴───────────────────────┴────┴────┴────┴────┘ └───┴───┴───┘ └───────┴───┴───┘
 */
object RxDataTool {

    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return `true`: 为空<br></br>`false`: 不为空
     */
    @JvmStatic
    fun isEmpty(obj: Any?): Boolean {
        if (obj == null) {
            return true
        }
        if (obj is String && obj.toString().isEmpty()) {
            return true
        }
        if (obj.javaClass.isArray && Array.getLength(obj) == 0) {
            return true
        }
        if (obj is Collection<*> && obj.isEmpty()) {
            return true
        }
        if (obj is Map<*, *> && obj.isEmpty()) {
            return true
        }
        if (obj is SparseArray<*> && obj.size() == 0) {
            return true
        }
        if (obj is SparseBooleanArray && obj.size() == 0) {
            return true
        }
        if (obj is SparseIntArray && obj.size() == 0) {
            return true
        }
        return obj is SparseLongArray && obj.size() == 0
    }

    /**
     * 判断字符串是否为空 为空即true
     *
     * @param str 字符串
     * @return
     */
    @JvmStatic
    fun isNullString(str: String?): Boolean {
        return str == null || str.isEmpty() || "null" == str
    }

    /**
     * 判断字符串是否是整数
     */
    @JvmStatic
    fun isInteger(value: String): Boolean {
        return try {
            value.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    /**
     * 判断字符串是否是双精度浮点数
     */
    @JvmStatic
    fun isDouble(value: String): Boolean {
        return try {
            value.toDouble()
            value.contains(".")
        } catch (e: NumberFormatException) {
            false
        }
    }

    /**
     * 判断字符串是否是数字
     */
    @JvmStatic
    fun isNumber(value: String): Boolean {
        return isInteger(value) || isDouble(value)
    }

    /**
     * 字符串转换成整数 ,转换失败将会 return 0;
     *
     * @param str 字符串
     * @return
     */
    @JvmStatic
    fun stringToInt(str: String): Int {
        return if (isNullString(str)) {
            0
        } else {
            try {
                str.toInt()
            } catch (e: NumberFormatException) {
                0
            }
        }
    }

    /**
     * 字符串转换成long ,转换失败将会 return 0;
     *
     * @param str 字符串
     * @return
     */
    @JvmStatic
    fun stringToLong(str: String): Long {
        return (if (isNullString(str)) {
            0L
        } else {
            try {
                str.toLong()
            } catch (e: NumberFormatException) {
                0L
            }
        })
    }

    /**
     * 字符串转换成double ,转换失败将会 return 0;
     *
     * @param str 字符串
     * @return
     */
    @JvmStatic
    fun stringToDouble(str: String): Double {
        return (if (isNullString(str)) {
            0.00
        } else {
            try {
                str.toDouble()
            } catch (e: NumberFormatException) {
                0.00
            }
        })
    }

    /**
     * 字符串转换成浮点型 Float
     *
     * @param str 待转换的字符串
     * @return 转换后的 float
     */
    @JvmStatic
    fun stringToFloat(str: String): Float {
        return (if (isNullString(str)) {
            0F
        } else {
            try {
                str.toFloat()
            } catch (e: NumberFormatException) {
                0F
            }
        })
    }

}