package com.xxd.view.third.banner

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.extend.binding
import com.xxd.common.util.log.LogUtil
import com.xxd.common.util.toast.ToastUtil
import com.xxd.view.R
import com.xxd.view.databinding.ViewFragmentBannerBinding
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
 *    测试轮播图控件
 */
class BannerFragment : BaseFragment() {

    var viewBinding by binding<ViewFragmentBannerBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ViewFragmentBannerBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

//        initBanner1()
        initBanner2()
//        initViewPager()

    }

    private fun initBanner2() {
        val list2 = mutableListOf(
            R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5, R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5,
            R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5, R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5
        )
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

    private fun initBanner1() {
        val list1 = mutableListOf(R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5)
        viewBinding.banner1.setAdapter(object : BannerImageAdapter<Int>(list1) {
            override fun onBindView(holder: BannerImageHolder, data: Int, position: Int, size: Int) {
                //  holder.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                holder.imageView.setImageBitmap(BitmapFactory.decodeResource(context!!.resources, data))
                // LogUtil.d("data:$data position=$position size=$size")
            }
        }.apply {
            setOnBannerListener { data, position ->
                ToastUtil.showToast("position=$position data=$data")
            }
        }).setOrientation(Banner.VERTICAL).addBannerLifecycleObserver(viewLifecycleOwner)
    }

    private fun initViewPager() {
        viewBinding.vp1.offscreenPageLimit = 2
        val list1 = mutableListOf(
            R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5, R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5,
            R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5, R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_3, R.drawable.view_bg_4, R.drawable.view_bg_5
        )
//        viewBinding.vp1.adapter = object : BaseBindingQuickAdapter<Int, ViewItemBanner2Binding>(list1) {
//
//            override fun convert(holder: BaseBindingViewHolder<ViewItemBanner2Binding>, item: Int) {
//                holder.binding.ivBg.setImageBitmap(BitmapFactory.decodeResource(context.resources, item))
////                LogUtil.d("viewHolder计算值：position从1开始=${holder.adapterPosition+1} data=$item")
//            }
//
//            override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<ViewItemBanner2Binding> {
////                LogUtil.d("新创建ViewHolder")
//                return super.onCreateDefViewHolder(parent, viewType)
//            }
//        }


        viewBinding.vp1.adapter = object : RecyclerView.Adapter<BaseBindingViewHolder<ViewItemBanner2Binding>>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<ViewItemBanner2Binding> {
                LogUtil.d("Recycler创建ViewHolder")
                return BaseBindingViewHolder(ViewItemBanner2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
            }

            override fun onBindViewHolder(holder: BaseBindingViewHolder<ViewItemBanner2Binding>, position: Int) {
                LogUtil.d("viewHolder计算值：position从1开始=${holder.adapterPosition + 1} 回调的position=$position")
                holder.binding.ivBg.setImageBitmap(BitmapFactory.decodeResource(context!!.resources, list1[position]))
            }

            override fun getItemCount(): Int {
                return list1.size
            }

        }

//        vpRegisterListener()
    }

    private fun vpRegisterListener() {
        viewBinding.vp1.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                /**
                 * 名字解释：
                 * 1. position : 当前viewpager最左侧的item的position
                 * 2. positionOffset : [0,1) position对应的条目已经移动的距离百分比（左移时候，因为拿的的position是上一个条目，所以是从0.99往0之间赋值）
                 * 3. positionOffsetPixels : 单位px, [0,ViewPager2的宽度) position对应的条目已经移动的px
                 * 默认回调： 0  0.0  0
                 * 滑动（包含释放后的自动滑动） ： position positionOffset positionOffsetPixels
                 * 滑动到边缘 ： 只回调1次 position 0 0
                 */
                LogUtil.d("VP2 -- onPageScrolled : position=$position positionOffset=$positionOffset positionOffsetPixels=$positionOffsetPixels")
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                /**
                 * 默认回调： 0
                 * 滑动 ： 无
                 * 滑动停止并自动滑到某个条目: position
                 * 滑动到边缘 ： 无
                 */
                LogUtil.d("VP2 -- onPageSelected : position=$position")
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                /**
                 * 默认回调： 无
                 * 滑动 ： 1 2 0  手指滑动——放手自动滑动——停止
                 * 滑动到边缘 ： 1 0 手指滑动——停止
                 */
                LogUtil.d("VP2 -- onPageScrollStateChanged : state=$state")
            }
        })
    }
}