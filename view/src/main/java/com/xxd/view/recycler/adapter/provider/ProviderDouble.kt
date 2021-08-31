package com.xxd.view.recycler.adapter.provider

import android.graphics.Color
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.extend.onClick
import com.xxd.common.util.toast.ToastUtil
import com.xxd.view.R

/**
 *    author : xxd
 *    date   : 2021/8/16
 *    desc   :
 */
class ProviderDouble : BaseItemProvider<ItemBase>() {

    override val itemViewType: Int
        get() = 3

    override val layoutId: Int
        get() = R.layout.common_simple_text

    override fun convert(helper: BaseViewHolder, item: ItemBase) {
        val view = helper.getView<TextView>(R.id.tv_name)
        view.setBackgroundColor(Color.RED)
        view.layoutParams.apply {
            this.height = 200
        }
        helper.setText(R.id.tv_name, "price：${(item as ItemBean3).price}")
    }

    override fun onClick(helper: BaseViewHolder, view: View, data: ItemBase, position: Int) {
        super.onClick(helper, view, data, position)
        view.onClick {
            data as ItemBean3
            ToastUtil.showToast(context,"价格点击：${data.price}")
        }
    }
}