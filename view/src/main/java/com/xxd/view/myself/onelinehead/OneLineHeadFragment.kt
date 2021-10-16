package com.xxd.view.myself.onelinehead

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.binding
import com.xxd.common.extend.onClick
import com.xxd.view.R
import com.xxd.view.databinding.ViewFragmentCustomImageBinding
import com.xxd.view.databinding.ViewFragmentOneLineBinding
import com.xxd.view.databinding.ViewFragmentOneLineHeadBinding

/**
 *    author : xxd
 *    date   : 2021/8/26
 *    desc   :
 */
class OneLineHeadFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentOneLineHeadBinding>()

    val list = listOf(R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4)
    val list2 = listOf(R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ViewFragmentOneLineHeadBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()


        viewBinding.oneLienLayout.setAdapter(object : OneLineHeadView.Adapter {
            override fun getCount(): Int {
                return list.size
            }

            override fun fillHead(position: Int, imageView: ImageView) {
                imageView.setImageResource(list[position])
            }
        })

        viewBinding.tv1.onClick {
            viewBinding.oneLienLayout.setAdapter(object : OneLineHeadView.Adapter {
                override fun getCount(): Int {
                    return list2.size
                }

                override fun fillHead(position: Int, imageView: ImageView) {
                    imageView.setImageResource(list2[position])
                }
            })
        }
    }
}