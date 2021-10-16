package com.xxd.common.extend

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.chad.library.adapter.base.viewholder.BaseViewHolder

// 扩展BaseQuickAdapter

/**
 *  从viewHolder中拿到数据，方便处理点击事件
 */
fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.getDataFromHolder(viewHolder: VH): T? {
    // 获取真实数据
    var position = viewHolder.adapterPosition
    if (position == RecyclerView.NO_POSITION) {
        return null
    }
    position -= headerLayoutCount
    return data[position]
}

/**
 *  从viewHolder中拿到position，方便处理点击事件
 */
fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.getPositionFromHolder(viewHolder: VH): Int {
    // 获取真实数据
    var position = viewHolder.adapterPosition
    if (position == RecyclerView.NO_POSITION) {
        return -1
    }
    position -= headerLayoutCount
    return position
}

/**
 * 设置数据，但是不修改loadMore模块的状态
 * 防止修改内部数据（比如：点击关注按钮，不涉及数据是否完整等），重新处理了加载跟多部分的逻辑
 */
fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.setListNoLoadMore(list: Collection<T>?) {
    if (list !== this.data) {
        this.data.clear()
        if (!list.isNullOrEmpty()) {
            this.data.addAll(list)
        }
    } else {
        if (!list.isNullOrEmpty()) {
            val newList = ArrayList(list)
            this.data.clear()
            this.data.addAll(newList)
        } else {
            this.data.clear()
        }
    }
//    notifyDataSetChanged()
}

fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.setOriginalList(list: MutableList<T>) {
    if (list !== this.data) {
        this.data.clear()
        if (!list.isNullOrEmpty()) {
            this.data.addAll(list)
        }
    } else {
        if (!list.isNullOrEmpty()) {
            val newList = ArrayList(list)
            this.data.clear()
            this.data.addAll(newList)
        } else {
            this.data.clear()
        }
    }
    notifyDataSetChanged()

}


/**
 *  将上拉加载部分从 false -> true时，必然会调用 loadMoreStatus = LoadMoreStatus.Complete，与自动加载跟多冲突
 *  处理isEnableLoadMore 在 false -> true 后，保持之前的下拉状态
 *  @param isLoadMoreAble 是否能加载更多
 */
fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.saveLoadMoreStatus(isLoadMoreAble: Boolean) {
    if (isLoadMoreAble) {
        loadMoreModule.apply {
            val preStatus = loadMoreStatus // 之前的状态
            isEnableLoadMore = true
            when (preStatus) { // 恢复之前的状态，防止触发自动加载更多
                LoadMoreStatus.End -> loadMoreEnd()
                LoadMoreStatus.Fail -> loadMoreFail()
                else -> {
                }
            }
        }
    } else {
        loadMoreModule.isEnableLoadMore = false
    }
}

/**
 * 同步用DiffUtil刷新adapter的扩展
 * 因为异步刷新会导致自动加载更多
 */
fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.setDiffSync(newList: MutableList<T>, block: (List<T>, List<T>) -> DiffUtil.Callback) {
    val callback = block(data, newList)
    val calculateDiff = DiffUtil.calculateDiff(callback)
    setDiffNewData(calculateDiff, newList)
}