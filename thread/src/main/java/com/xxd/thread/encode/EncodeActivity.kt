package com.xxd.thread.encode

import androidx.fragment.app.Fragment
import com.xxd.common.fast.SimpleSwitchFragmentActivity
import java.lang.IllegalArgumentException

/**
 * author : xxd
 * date   : 2020/9/20
 * desc   :
 */
class EncodeActivity : SimpleSwitchFragmentActivity() {

    private val dataList = listOf("Object序列化")

    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getPositionFragment(position: Int): Fragment {
        return when (position) {
            0 -> ObjectEncodeFragment()
            else -> throw IllegalArgumentException()
        }
    }

    override fun getTitleName(): CharSequence? {
        return "序列化"
    }
}