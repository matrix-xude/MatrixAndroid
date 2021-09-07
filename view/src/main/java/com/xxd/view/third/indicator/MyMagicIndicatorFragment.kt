package com.xxd.view.third.indicator

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.databinding.CommonItemSimpleImageViewBinding
import com.xxd.common.extend.binding
import com.xxd.common.extend.onClick
import com.xxd.common.util.device.DimensionUtil
import com.xxd.view.R
import com.xxd.view.databinding.ViewFragmentMyMagicIndicatorBinding
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

/**
 *    author : xxd
 *    date   : 2021/8/23
 *    desc   : 使用 MagicIndicator 的各种属性
 */
class MyMagicIndicatorFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentMyMagicIndicatorBinding>()
    val imageList = mutableListOf(R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5)
    val titleList = mutableListOf("标题1", "标题2", "标题3", "标题4", "标题5")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ViewFragmentMyMagicIndicatorBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

        val commonNavigator = CommonNavigator(context).apply {
//            isFollowTouch = false
            isAdjustMode = true
        }
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return titleList.size
            }

            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                return SimplePagerTitleView(context).apply {
                    normalColor = Color.BLACK
                    selectedColor = Color.RED
                    currentTextColor
                    text = titleList[index]
                    onClick {
//                        viewBinding.magicIndicator.onPageSelected(index)
                        viewBinding.magicIndicator.onPageScrolled(index,0f,0)
                    }
                }
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                return CustomLinePagerIndicator(context).apply {
                    mode = LinePagerIndicator.MODE_EXACTLY
                    lineWidth = DimensionUtil.dp2px(context!!,26f).toFloat()
                    lineHeight = DimensionUtil.dp2px(context,3f).toFloat()
                    setGradientColors(Color.RED,Color.BLUE)
                }
            }
        }
        viewBinding.magicIndicator.navigator = commonNavigator

    }

    private fun bindingIndicator2VP() {
        viewBinding.vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                viewBinding.magicIndicator.onPageScrolled(
                    position,
                    positionOffset,
                    positionOffsetPixels
                )
            }

            override fun onPageSelected(position: Int) {
                viewBinding.magicIndicator.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                viewBinding.magicIndicator.onPageScrollStateChanged(state)
            }
        })
    }

    private fun initViewPager2() {
        viewBinding.vp2.adapter = object :
            RecyclerView.Adapter<BaseBindingViewHolder<CommonItemSimpleImageViewBinding>>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<CommonItemSimpleImageViewBinding> {
                val viewBinding = CommonItemSimpleImageViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return BaseBindingViewHolder(viewBinding)
            }

            override fun onBindViewHolder(holder: BaseBindingViewHolder<CommonItemSimpleImageViewBinding>, position: Int) {
                holder.binding.ivIcon.setImageBitmap(BitmapFactory.decodeResource(context!!.resources, imageList[position]))
            }

            override fun getItemCount(): Int {
                return imageList.size
            }
        }
    }

}