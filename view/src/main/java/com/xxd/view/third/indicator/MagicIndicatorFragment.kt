package com.xxd.view.third.indicator

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.databinding.CommonItemSimpleImageViewBinding
import com.xxd.common.extend.binding
import com.xxd.common.extend.onClick
import com.xxd.view.R
import com.xxd.view.databinding.ViewFragmentMagicIndicatorBinding
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

/**
 *    author : xxd
 *    date   : 2021/8/23
 *    desc   :
 */
class MagicIndicatorFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentMagicIndicatorBinding>()
    val imageList = mutableListOf(
        R.drawable.view_bg_1,
        R.drawable.view_bg_2,
        R.drawable.view_bg_3,
        R.drawable.view_bg_4,
        R.drawable.view_bg_5
    )
    val titleList = mutableListOf("标题1", "标题2", "标题3", "标题4", "标题5")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = ViewFragmentMagicIndicatorBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

        viewBinding.vp1.adapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return imageList.size
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val imageView = ImageView(context)
                imageView.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                imageView.setImageBitmap(
                    BitmapFactory.decodeResource(context!!.resources, imageList[position])
                )
                container.addView(imageView)
                return imageView
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }

        viewBinding.vp2.adapter = object :
            RecyclerView.Adapter<BaseBindingViewHolder<CommonItemSimpleImageViewBinding>>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): BaseBindingViewHolder<CommonItemSimpleImageViewBinding> {
                val viewBinding = CommonItemSimpleImageViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return BaseBindingViewHolder(viewBinding)
            }

            override fun onBindViewHolder(
                holder: BaseBindingViewHolder<CommonItemSimpleImageViewBinding>,
                position: Int
            ) {
                holder.binding.ivIcon.setImageBitmap(
                    BitmapFactory.decodeResource(
                        context!!.resources,
                        imageList[position]
                    )
                )
            }

            override fun getItemCount(): Int {
                return imageList.size
            }
        }

        val commonNavigator = CommonNavigator(context).apply {
            isFollowTouch = false
        }
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return imageList.size
            }

            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                return ColorTransitionPagerTitleView(context).apply {
                    normalColor = Color.BLACK
                    selectedColor = Color.RED
                    text = titleList[index]
                    onClick {
                        viewBinding.vp1.currentItem = index
                        viewBinding.vp2.currentItem = index
                    }
                }
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                return LinePagerIndicator(context).apply {
                    mode = LinePagerIndicator.MODE_WRAP_CONTENT
                }
            }
        }
        viewBinding.magicIndicator.navigator = commonNavigator
        ViewPagerHelper.bind(viewBinding.magicIndicator, viewBinding.vp1)

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

}