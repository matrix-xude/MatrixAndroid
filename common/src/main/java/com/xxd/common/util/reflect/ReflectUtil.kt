package com.xxd.common.util.reflect

import java.lang.reflect.Type

/**
 *    author : xxd
 *    date   : 2021/7/13
 *    desc   : 反射工具类
 */
object ReflectUtil {

    /**
     * 获取某个目标父类的Type,一般是为了获取父类的泛型而调用
     * @param clazz 当前开始查找的类型
     * @param targetClass 目标类型
     */
    fun genericSuperclass(clazz: Class<*>, targetClass: Class<*>): Type? {
        var indexClazz: Class<*>? = clazz
        while (indexClazz != null && indexClazz.superclass != targetClass) {
            indexClazz = clazz.superclass
        }
        return indexClazz?.genericSuperclass
    }
}