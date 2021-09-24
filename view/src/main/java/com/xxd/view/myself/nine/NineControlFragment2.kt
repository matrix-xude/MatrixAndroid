package com.xxd.view.myself.nine

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.costom.decoration.CommonItemDecoration
import com.xxd.common.extend.binding
import com.xxd.common.util.log.LogUtil
import com.xxd.view.R
import com.xxd.view.databinding.ViewFragmentNineControl2Binding
import com.xxd.view.databinding.ViewItemNineViewSpecialBinding

/**
 *    author : xxd
 *    date   : 2021/8/26
 *    desc   :
 */
class NineControlFragment2 : BaseFragment() {

    private var viewBinding by binding<ViewFragmentNineControl2Binding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = ViewFragmentNineControl2Binding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

        initRecyclerView()

    }

    private fun initRecyclerView() {
//        val list = mutableListOf(1, 8, 4, 15, 0, 3, 1, 2, 7, 93, 5, 1, 23, 5, 1, 5, 8, 2, 1, 5, 1, 8, 4, 15, 0, 3, 1, 2, 7, 93, 5, 1, 23, 5, 1, 5, 8, 2, 1, 5)
        val list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 93, 5, 1, 23, 5, 1, 5, 8, 2, 1, 5, 1, 8, 4, 15, 0, 3, 1, 2, 7, 93, 5, 1, 23, 5, 1, 5, 8, 2, 1, 5)

        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(CommonItemDecoration().apply {
                interval = 10
                boundary = 10
            })
            adapter = object : BaseBindingQuickAdapter<Int, ViewItemNineViewSpecialBinding>() {

                override fun convert(holder: BaseBindingViewHolder<ViewItemNineViewSpecialBinding>, item: Int) {
                    holder.binding.nineControlView.setAdapter(object : NineControlSpecialView.Adapter {
                        override fun getCount(): Int {
                            return item
                        }

                        override fun createView(position: Int): View {
                            return ImageView(context).apply {
                                setBackgroundColor(Color.RED)
                                if (getCount() == 1) {
                                    Glide.with(this).asBitmap().load(R.drawable.view_bg_long_height_3).into(object : SimpleTarget<Bitmap>() {
                                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                            detailScaleType(this@apply, resource.width, resource.height)

                                            post { setImageBitmap(resource) }
                                        }
                                    })
                                } else {
                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                    Glide.with(context).load(R.drawable.view_bg_6).into(this)
//                                setImageBitmap( BitmapFactory.decodeResource(context.resources,R.drawable.view_bg_6))
                                }
                            }
                        }

                    })
                }
            }.apply {
                setList(list)
            }
        }
    }

    // 处理图片的模式，长图标记
    private fun detailScaleType(imageView: ImageView, bitmapWidth: Int, bitmapHeight: Int) {
        imageView.layoutParams = ViewGroup.LayoutParams(bitmapWidth, bitmapHeight)
        imageView.post {  // 保证该代码在 layoutParams 之后执行，可以拿到新参数设置后view的width
            var scaleTypeCenter = true // 图片的模式有2种，true:CENTER_CROP; false:MATRIX
            var isLongBitmap = false // 当前图片是否为"长图"
            when {
                imageView.width == imageView.height -> {  // 只有1-3，1-4会出现此情况，1-3中，宽高比<1:3需要处理
                    if (bitmapWidth * 3 < bitmapHeight)
                        scaleTypeCenter = false
                }
                imageView.width < imageView.height -> { // 只有1-2出现此情况，当前ImageView的 宽：高 = 3 ：4
                    if (bitmapWidth * 4 / 3 < bitmapHeight)
                        scaleTypeCenter = false
                    if (bitmapWidth * 3 <= bitmapHeight)
                        isLongBitmap = true
                }
                else -> { // 只有1-1出现此情况，当前ImageView的 宽：高 = 4 ：3
                    if (bitmapWidth >= bitmapHeight * 3)
                        isLongBitmap = true
                }
            }
            // 处理ImageView的模式
            if (scaleTypeCenter) {
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                val scale = imageView.width.toFloat() / bitmapWidth.toFloat()
                val matrix = Matrix().apply { preScale(scale, scale, 0f, 0f) }
                imageView.scaleType = ImageView.ScaleType.MATRIX
                imageView.imageMatrix = matrix
            }
            // 处理长图
            if (isLongBitmap){
                LogUtil.d("当前图片是 长图")
            }
        }
    }

}