package com.xxd.view.material.tablayout

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 *    author : xxd
 *    date   : 2021/7/26
 *    desc   :
 */
class VPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return TopicBaseFragment(position)
    }
}