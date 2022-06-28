package com.xxd.kt.basic.data

/**
 *    author : xxd
 *    date   : 2022/4/24
 *    desc   : 测试data类是否能覆写函数
 *    因为 data 类不能 open，只能通过接口来实现公用数据处理，结论是可行的
 */
interface IBaseData {
    val id : Long
    val desc : String
}