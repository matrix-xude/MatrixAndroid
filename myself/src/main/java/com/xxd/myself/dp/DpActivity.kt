package com.xxd.myself.dp

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.xxd.common.base.BaseApplication
import com.xxd.common.fast.SimpleSwitchFragmentActivity

/**
 *    author : xxd
 *    date   : 2023/12/26
 *    desc   :
 */
class DpActivity : SimpleSwitchFragmentActivity() {

    private val dataList = listOf("px", "dpi","smallestWidth适配","今日头条适配")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getDataList(): Collection<String> {
        return dataList
    }

    override fun getPositionFragment(position: Int): Fragment {
        return when (position) {
            0 -> PxFragment()
            1 -> DpiFragment()
            2 -> SmallestWidthFragment()
            3 -> DensityFragment()
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun getTitleName(): CharSequence {
        return "Px Dp研究"
    }
}