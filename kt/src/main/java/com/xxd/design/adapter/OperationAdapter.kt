package com.xxd.design.adapter

/**
 *    author : xxd
 *    date   : 2023/11/23
 *    desc   :
 */
class OperationAdapter : INewOperation {

    private val oldOperation = OldOperation()

    override fun getAge(): String {
        // 就是一个适配转换的过程
        return "${oldOperation.getAge()}"
    }


}