package com.xxd.view.recycler

import androidx.recyclerview.widget.DiffUtil
import com.xxd.common.util.log.LogUtil

/**
 *    author : xxd
 *    date   : 2021/7/14
 *    desc   : CallBack 统计
 */
class MyDiff<T>(private val oldList: List<T>?, private val newList: List<T>?) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        val size = oldList?.size ?: 0
        LogUtil.d("老数据size : $size")
        return size
    }

    override fun getNewListSize(): Int {
        val size = newList?.size ?: 0
        LogUtil.d("新数据size : $size")
        return size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val b = oldList?.get(oldItemPosition) === newList?.get(newItemPosition)
        LogUtil.d("item数据是否相等 -> oldItemPosition：$oldItemPosition ; newItemPosition:$newItemPosition ; 是否相等：$b")
        return b
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val b = oldList?.get(oldItemPosition) == newList?.get(newItemPosition)
        LogUtil.d("content内柔是否相等 -> oldItemPosition：$oldItemPosition ; newItemPosition:$newItemPosition ; 是否相等：$b")
        return b
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        LogUtil.d("getChangePayload被调用 -> oldItemPosition：$oldItemPosition ; newItemPosition:$newItemPosition")
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}