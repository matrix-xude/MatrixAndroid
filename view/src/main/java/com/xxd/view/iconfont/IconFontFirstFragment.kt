package com.xxd.view.iconfont

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.binding
import com.xxd.view.R
import com.xxd.view.databinding.ViewFragmentIconFontFirstBinding

/**
 *    author : xxd
 *    date   : 2021/8/9
 *    desc   :
 */
class IconFontFirstFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentIconFontFirstBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = ViewFragmentIconFontFirstBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

        val iconfont = Typeface.createFromAsset(requireActivity().assets, "iconfont.ttf")
        viewBinding.tv1.typeface = iconfont
        viewBinding.tv2.typeface = iconfont
        viewBinding.tv3.typeface = iconfont
        viewBinding.tv4.typeface = iconfont
        viewBinding.tv5.typeface = iconfont
        viewBinding.tv6.typeface = iconfont

//        viewBinding.tv7.text = getString(R.string.view_icon_font) + "高大上"
        viewBinding.tv7.typeface = iconfont
//        viewBinding.tv7.requestLayout()


        val font = "&#xe60e;"
        val iconStr = getString(R.string.view_icon_font)

        val spanString = SpannableStringBuilder("${iconStr}高大上")
        val foregroundColorSpan = ForegroundColorSpan(Color.RED)
        val absoluteSizeSpan = AbsoluteSizeSpan(80)
        spanString.setSpan(
            foregroundColorSpan,
            0,
            iconStr.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanString.setSpan(
            absoluteSizeSpan,
            0,
            iconStr.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        val foregroundColorSpan2 = ForegroundColorSpan(Color.BLUE)
        spanString.setSpan(
            foregroundColorSpan2,
            iconStr.length,
            spanString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )




        viewBinding.tv7.text = spanString

    }
}