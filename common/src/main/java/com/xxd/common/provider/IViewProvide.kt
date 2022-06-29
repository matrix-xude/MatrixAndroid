package com.xxd.common.provider

import com.alibaba.android.arouter.facade.template.IProvider

/**
 *    author : xxd
 *    date   : 2022/6/28
 *    desc   : 测试ARouter的Provider
 */
interface IViewProvide : IProvider {

    fun show(string: String)
}