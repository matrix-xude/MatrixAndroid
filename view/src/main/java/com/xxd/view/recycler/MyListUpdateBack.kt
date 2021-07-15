package com.xxd.view.recycler

import androidx.recyclerview.widget.ListUpdateCallback
import com.xxd.common.util.log.LogUtil

/**
 *    author : xxd
 *    date   : 2021/7/15
 *    desc   :
 */
class MyListUpdateBack : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {
        LogUtil.d("~~~~~~~ onInserted -> position：$position ; count:$count")
    }

    override fun onRemoved(position: Int, count: Int) {
        LogUtil.d("~~~~~~~ onRemoved -> position：$position ; count:$count")
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        LogUtil.d("~~~~~~~ onMoved -> fromPosition：$fromPosition ; toPosition:$toPosition")
    }

    override fun onChanged(position: Int, count: Int, payload: Any?) {
        LogUtil.d("~~~~~~~ onChanged -> position：$position ; count:$count ; payload:$payload")
    }
}