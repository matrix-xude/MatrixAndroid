package com.xxd.view.bigpic

import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.logger.Logger
import com.tamsiree.rxkit.RxDataTool
import com.xxd.common.base.activity.BaseActivity
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.databinding.CommonItemHorizontalSimpleTextBinding
import com.xxd.common.databinding.CommonSimpleTextBinding
import com.xxd.common.util.toast.ToastUtil
import com.xxd.view.R
import com.xxd.view.bigpic.photo.MovePhotoView
import com.xxd.view.databinding.ViewActivityBigPictureBinding
import com.xxd.view.databinding.ViewFragmentBigPictureBinding

/**
 *    author : xxd
 *    date   : 2022/8/15
 *    desc   : 使用了第三方PhotoView的大图页
 */
class BigPictureActivity : BaseActivity() {


    private val picList = listOf(
        R.drawable.view_bg_1, R.drawable.view_bg_2
    )
    /*private val picList = listOf(
        R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_long_height, R.drawable.view_bg_gif_1,
        R.drawable.view_bg_5, R.drawable.view_bg_6, R.drawable.view_bg_3, R.drawable.view_bg_4
    )*/

    private lateinit var viewBinding: ViewActivityBigPictureBinding

    private lateinit var adapter: BaseBindingQuickAdapter<Int, ViewFragmentBigPictureBinding>

    override fun getContentViewBase(): View {
        viewBinding = ViewActivityBigPictureBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.common_transparent)
        }

        ViewCompat.setTransitionName(viewBinding.viewPager2, "shareView")
        initViewPager2()
        viewBinding.horizontalPullLayout.needHorizontalRefresh = true
        viewBinding.horizontalPullLayout.releaseListener = {
            Logger.d("我加载下一页")
        }

    }

    override fun initData() {
        super.initData()

        adapter.setList(picList)
    }

    private fun initViewPager2() {

        adapter = object : BaseBindingQuickAdapter<Int, ViewFragmentBigPictureBinding>() {
            override fun convert(holder: BaseBindingViewHolder<ViewFragmentBigPictureBinding>, item: Int) {
                Glide.with(context).load(item).into(holder.binding.photoView)
            }

            override fun onItemViewHolderCreated(viewHolder: BaseBindingViewHolder<ViewFragmentBigPictureBinding>, viewType: Int) {
                viewHolder.binding.photoView.movePhotoListener = object : MovePhotoView.MovePhotoListener {
                    override fun moveUp() {
                        onBackPressed()
//                        finish()
//                        overridePendingTransition(R.anim.view_big_pic_alpha_in,R.anim.view_big_pic_alpha_out)
                    }

                    override fun moving(dx: Float, dy: Float) {
                        val dist = viewHolder.binding.photoView.moveDragDistance
                        val alpha = if (dy < dist) 1f else (1 - (dy - dist) / 1000).coerceAtLeast(0f)
                        viewBinding.vBg.alpha = alpha
                    }

                    override fun click() {
                        ToastUtil.showToast("单击！！")
                    }

                    override fun longClick() {
                        ToastUtil.showToast("长按！！")
                    }

                }
            }
        }

        viewBinding.viewPager2.adapter = this.adapter

    }
}