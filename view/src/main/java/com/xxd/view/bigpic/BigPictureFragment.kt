package com.xxd.view.bigpic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.extend.binding
import com.xxd.common.extend.onClick
import com.xxd.common.util.toast.ToastUtil
import com.xxd.view.R
import com.xxd.view.bigpic.photo.MovePhotoView
import com.xxd.view.databinding.ViewFragmentBigPictureBinding

/**
 *    author : xxd
 *    date   : 2022/8/15
 *    desc   : 根据index加载测试图片
 */
class BigPictureFragment(private val index: Int) : BaseFragment() {

    private var viewBinding by binding<ViewFragmentBigPictureBinding>()
    private val picList = listOf(
        R.drawable.view_bg_1, R.drawable.view_bg_2, R.drawable.view_bg_long_height, R.drawable.view_bg_gif_1,
        R.drawable.view_bg_5, R.drawable.view_bg_6, R.drawable.view_bg_3, R.drawable.view_bg_4
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = ViewFragmentBigPictureBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.d("当前index=${index}的FragmentView被销毁了")
    }

    override fun initView() {

        initPhotoView()
        initListener()
    }

    private fun initPhotoView() {
        viewBinding.photoView.movePhotoListener = object : MovePhotoView.MovePhotoListener {
            override fun moveUp() {
                requireActivity().finish()
            }

            override fun moving(dx: Float, dy: Float) {
                val dist = viewBinding.photoView.moveDragDistance
                val alpha = if (dy < dist) 1f else (1 - (dy - dist) / 1000).coerceAtLeast(0f)
                viewBinding.photoView.alpha = alpha
            }

            override fun click() {
                ToastUtil.showToast("单击！！")
            }

            override fun longClick() {
                ToastUtil.showToast("长按！！")
            }

        }
    }

    private fun initListener() {
        viewBinding.tv1.onClick {
            val attacher = viewBinding.photoView.attacher
            Logger.d(
                "当前图片的参数：\nminimumScale=${attacher.minimumScale}\nmaximumScale=${attacher.maximumScale}\n" +
                        "${attacher.imageMatrix}"
            )
        }

        viewBinding.tv2.onClick {
            viewBinding.photoView.setScale(3f, 0f, 0f, true)
        }
    }

    override fun initDataImmediately() {
//        viewBinding.photoView.setImageResource(picList[index])

        Glide.with(this).load(picList[index]).into(viewBinding.photoView)
    }

    override fun initDataLazy() {
//        viewBinding?.photoView?.setImageResource(picList[index])
    }
}