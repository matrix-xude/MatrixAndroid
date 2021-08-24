package com.xxd.view.third.banner

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.extend.binding
import com.xxd.common.extend.onClick
import com.xxd.common.util.log.LogUtil
import com.xxd.common.util.toast.ToastUtil
import com.xxd.view.R
import com.xxd.view.databinding.ViewFragmentBanner2Binding
import com.xxd.view.databinding.ViewItemBanner2Binding
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener

/**
 *    author : xxd
 *    date   : 2021/8/18
 *    desc   : https://github.com/youth5201314/banner
 *    测试轮播图控件刷新数据导致的各种显示不准确的问题
 */
class BannerFragment2 : BaseFragment() {

    val mList = mutableListOf<Int>()
    val list1 = mutableListOf(R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5)
    var viewBinding by binding<ViewFragmentBanner2Binding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ViewFragmentBanner2Binding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

        initBanner1()
//        initBanner2()

        viewBinding.tv1.onClick {
            viewBinding.banner1.setDatas(list1)
        }

    }

    private fun initBanner1() {

        viewBinding.banner1.setAdapter(object : BannerImageAdapter<Int>(mList) {
            override fun onBindView(holder: BannerImageHolder, data: Int, position: Int, size: Int) {
                //  holder.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                holder.imageView.setImageBitmap(BitmapFactory.decodeResource(context!!.resources, data))
                // LogUtil.d("data:$data position=$position size=$size")
            }
        }.apply {
            setOnBannerListener { data, position ->
                ToastUtil.showToast("position=$position data=$data")
            }
        }).setOrientation(Banner.HORIZONTAL).addBannerLifecycleObserver(viewLifecycleOwner).indicator =
            CircleIndicator(context)
    }

    private fun initBanner2() {
        val list2 = mutableListOf(R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5, R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5,
                R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5, R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5)
        viewBinding.banner2.setAdapter(object : BannerAdapter<Int, BaseBindingViewHolder<ViewItemBanner2Binding>>(list2) {

            override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BaseBindingViewHolder<ViewItemBanner2Binding> {
                LogUtil.d("新创建ViewHolder")
                val viewBinding = ViewItemBanner2Binding.inflate(LayoutInflater.from(context), parent, false)
                return BaseBindingViewHolder(viewBinding)
            }

            override fun onBindView(holder: BaseBindingViewHolder<ViewItemBanner2Binding>, data: Int, position: Int, size: Int) {
                holder.binding.ivBg.setImageBitmap(BitmapFactory.decodeResource(context!!.resources, data))
                val position2 = holder.adapterPosition
                LogUtil.d("viewHolder计算值：position=$position2")
                LogUtil.d("回调值：position=$position data=${getData(position)}")
            }

        })
                .indicator = CircleIndicator(context)



        viewBinding.banner2.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                LogUtil.d("Banner -- Scrolled : position=$position positionOffset=$positionOffset positionOffsetPixels=$positionOffsetPixels")
            }

            override fun onPageSelected(position: Int) {
                LogUtil.d("Banner -- Selected : position=$position")
            }

            override fun onPageScrollStateChanged(state: Int) {
                LogUtil.d("Banner -- ScrollStateChanged : state=$state")
            }

        })
    }





}