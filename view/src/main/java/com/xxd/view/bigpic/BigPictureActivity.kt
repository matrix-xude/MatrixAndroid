package com.xxd.view.bigpic

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gyf.immersionbar.ktx.immersionBar
import com.xxd.common.base.activity.BaseActivity
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.view.R
import com.xxd.view.databinding.ViewActivityBigPictureBinding

/**
 *    author : xxd
 *    date   : 2022/8/15
 *    desc   : 使用了第三方PhotoView的大图页
 */
class BigPictureActivity : BaseActivity() {

    private lateinit var viewBinding: ViewActivityBigPictureBinding

    override fun getContentViewBase(): View {
        viewBinding = ViewActivityBigPictureBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.common_transparent)
        }
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