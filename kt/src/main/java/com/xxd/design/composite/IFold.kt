package com.xxd.design.composite

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   : 透明抽象模式
 */
interface IFold {

    fun isFolder(): Boolean  // 是否为文件夹

    fun subFolds(): List<IFold>  // 获取所有子文件

    fun readIt(): String  // 非文件夹才能读取

    fun show()
}