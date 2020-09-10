package com.xxd.view.test

import androidx.fragment.app.Fragment
import com.xxd.common.fast.SimpleSwitchFragmentActivity

/**
 *    author : xxd
 *    date   : 2020/9/3
 *    desc   :
 */
class TempTestActivity : SimpleSwitchFragmentActivity() {

    override fun getDataList(): Collection<String> {
        return listOf("")
    }

    override fun getPositionFragment(position: Int): Fragment {
        TODO()
    }

    override fun getTitleName(): CharSequence? {
        return "临时测试各种"
    }
}