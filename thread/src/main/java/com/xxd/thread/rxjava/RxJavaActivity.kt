package com.xxd.thread.rxjava

import androidx.fragment.app.Fragment
import com.xxd.common.fast.SimpleSwitchFragmentActivity
import java.lang.IllegalArgumentException

/**
 *    author : xxd
 *    date   : 2020/9/29
 *    desc   :
 */
class RxJavaActivity : SimpleSwitchFragmentActivity() {

    private val dataList = listOf("RxJava基础")

    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getPositionFragment(position: Int): Fragment {
        return when(position){
            0 -> RxJavaBasicFragment()
            else -> throw IllegalArgumentException()
        }
    }

    override fun getTitleName(): CharSequence? {
        return "RxJava"
    }
}