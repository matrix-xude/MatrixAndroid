package com.xxd.myself.dp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.BoringLayout.Metrics
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.InspectableProperty.ValueType
import androidx.annotation.RequiresApi
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.deepCopy
import com.xxd.common.extend.onClick
import com.xxd.myself.databinding.MyselfFragmentPxBinding

/**
 *    author : xxd
 *    date   : 2023/12/26
 *    desc   :
 */
class PxFragment : BaseFragment() {

    private lateinit var viewBinding: MyselfFragmentPxBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = MyselfFragmentPxBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        initListener()
    }


    @SuppressLint("SetTextI18n")
    private fun initListener() {
        // 获取屏幕宽高px
        viewBinding.tv3.onClick {
            getMetrics1()
        }
        viewBinding.tv4.onClick {
            getMetrics2()
        }
        viewBinding.tv5.onClick {
            getMetrics3()
        }
//        viewBinding.tv1.onClick{
//            viewBinding.tv1.text = "tv5 : width=${viewBinding.tv5.width},height=${viewBinding.tv5.height}"
//        }
//        viewBinding.tv2.onClick{
//            viewBinding.tv2.text = "sv1 : width=${viewBinding.sv1.width},height=${viewBinding.sv1.height}"
//        }
    }

    @SuppressLint("SetTextI18n")
    private fun getMetrics1() {
        val display = requireActivity().windowManager.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)

        viewBinding.tv3.text =
            "widthPixels=${metrics.widthPixels}\n" +
                    "heightPixels=${metrics.heightPixels}\n" +
                    "xdpi=${metrics.xdpi}\n" +  // 获取的xdpi计算物理尺寸出了问题
                    "ydpi=${metrics.ydpi}\n" +
                    "density=${metrics.density}\n" +
                    "densityDpi=${metrics.densityDpi}\n" +
                    "scaledDensity=${metrics.scaledDensity}" // 此方式字体缩放后 scaledDensity 错误
    }

    @SuppressLint("SetTextI18n")
    private fun getMetrics2() {
        val metrics = resources.displayMetrics

        viewBinding.tv4.text =
            "widthPixels=${metrics.widthPixels}\n" +
                    "heightPixels=${metrics.heightPixels}\n" +
                    "xdpi=${metrics.xdpi}\n" +  // 获取的xdpi计算物理尺寸出了问题
                    "ydpi=${metrics.ydpi}\n" +
                    "density=${metrics.density}\n" +
                    "densityDpi=${metrics.densityDpi}\n" +
                    "scaledDensity=${metrics.scaledDensity}" // 此方式字体缩放后 scaledDensity 正确
    }

    // 低于Api 30 的手机调用会崩溃
//    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("NewApi", "SetTextI18n")
    private fun getMetrics3() {
        val windowMetrics = requireActivity().windowManager.currentWindowMetrics
        val windowInsets = windowMetrics.windowInsets
        val bounds = windowMetrics.bounds

        viewBinding.tv5.text = "bounds=$bounds\n\n" +
                "windowInsets=$windowInsets"
    }


}