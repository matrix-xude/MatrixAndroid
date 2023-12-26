package com.xxd.myself.dp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xxd.common.base.BaseApplication
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.onClick
import com.xxd.common.util.intent.IntentUtil
import com.xxd.myself.databinding.MyselfFragmentDensityBinding

/**
 *    author : xxd
 *    date   : 2023/12/26
 *    desc   : 今日头条适配方式，修改metrics的density
 */
class DensityFragment : BaseFragment() {

    private lateinit var viewBinding: MyselfFragmentDensityBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = MyselfFragmentDensityBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        initListener()
    }

    private fun initDensityAdapter() {
        DensityUtil.initCustomDensity(requireActivity(), BaseApplication.application)
    }


    private fun initListener() {
        viewBinding.tv0.onClick {
            initDensityAdapter()
        }
        viewBinding.tv1.onClick {
            viewBinding.tv1.text = viewBinding.tv1.width.toString()
        }
        viewBinding.tv2.onClick {
            viewBinding.tv2.text = viewBinding.tv2.width.toString()
        }
        viewBinding.tv3.onClick {
            IntentUtil.startActivity<DpActivity>(requireContext())
        }
        viewBinding.tv4.onClick {
            textMetrics()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun textMetrics() {
        val metrics = BaseApplication.application.resources.displayMetrics

        viewBinding.tv4.text =
            "widthPixels=${metrics.widthPixels}\n" +
                    "heightPixels=${metrics.heightPixels}\n" +
                    "xdpi=${metrics.xdpi}\n" +  // 获取的xdpi计算物理尺寸出了问题
                    "ydpi=${metrics.ydpi}\n" +
                    "density=${metrics.density}\n" +
                    "densityDpi=${metrics.densityDpi}\n" +
                    "scaledDensity=${metrics.scaledDensity}" // 此方式字体缩放后 scaledDensity 正确
    }


}