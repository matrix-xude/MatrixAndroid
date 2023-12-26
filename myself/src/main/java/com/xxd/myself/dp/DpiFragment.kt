package com.xxd.myself.dp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.onClick
import com.xxd.myself.R
import com.xxd.myself.databinding.MyselfFragmentDpiBinding

/**
 *    author : xxd
 *    date   : 2023/12/26
 *    desc   : 测试Dpi的对于Drawable缩放的作用
 */
class DpiFragment : BaseFragment() {

    private lateinit var viewBinding: MyselfFragmentDpiBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = MyselfFragmentDpiBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        initListener()
    }


    @SuppressLint("SetTextI18n")
    private fun initListener() {
        viewBinding.tv5.onClick {
            viewBinding.tv5.text = "ImageView1 width=${viewBinding.iv1.width},height=${viewBinding.iv1.height}\n" +
                    "ImageView2 width=${viewBinding.iv2.width},height=${viewBinding.iv2.height}\n" +
                    "ImageView3 width=${viewBinding.iv3.width},height=${viewBinding.iv3.height}\n" +
                    "ImageView4 width=${viewBinding.iv4.width},height=${viewBinding.iv4.height}"
        }

    }




}