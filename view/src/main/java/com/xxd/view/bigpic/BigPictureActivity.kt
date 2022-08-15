package com.xxd.view.bigpic

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.view.databinding.ViewActivityBigPictureBinding

/**
 *    author : xxd
 *    date   : 2022/8/15
 *    desc   : 使用了第三方PhotoView的大图页
 */
class BigPictureActivity : BaseTitleActivity() {

    private lateinit var viewBinding: ViewActivityBigPictureBinding

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = ViewActivityBigPictureBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence {
        return "大图页"
    }

    override fun initView() {
        super.initView()
        initViewPager2()
    }

    private fun initViewPager2() {
        viewBinding.viewPager2.adapter = object : FragmentStateAdapter(this) {

            override fun getItemCount(): Int {
                return 8
            }

            override fun createFragment(position: Int): Fragment {
                return BigPictureFragment(position)
            }
        }

    }
}