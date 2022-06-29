package com.xxd.view

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.xxd.common.provider.IViewProvide
import com.xxd.common.util.log.LogUtil

/**
 *    author : xxd
 *    date   : 2022/6/28
 *    desc   :
 */
@Route(path = "/view/ViewProvideImpl")
class ViewProvideImpl : IViewProvide {

    override fun init(context: Context?) {
    }

    override fun show(string: String) {
        LogUtil.d("com.xxd.view.ViewProvideImpl fun() show $string")
    }
}