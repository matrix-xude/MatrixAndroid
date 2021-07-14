package com.xxd.view.systemWidget

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.databinding.CommonSimpleTextBinding
import com.xxd.common.extend.binding
import com.xxd.view.R
import com.xxd.view.databinding.ViewFragmentFlipperBinding

/**
 *    author : xxd
 *    date   : 2020/8/20
 *    desc   : ViewFlipper,AdapterViewFlipper
 */
class ViewFlipperFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentFlipperBinding>()
    private val dataList = mutableListOf<String>()
    private lateinit var adapter: BaseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = ViewFragmentFlipperBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()
        val stringArray = resources.getStringArray(R.array.view_quantum_mechanics)
        dataList.addAll(stringArray)
        initViewFlipper()
        initAdapterViewFlipper()
    }

    override fun initDataLazy() {
        super.initDataLazy()
        adapter.notifyDataSetChanged()
        viewBinding.adapterViewFlipper.startFlipping()
        viewBinding.viewFlipper.startFlipping()
    }

    /**
     * AdapterViewFlipper 不需要一次性加载所有view
     * convertView 回调参数每次都为null,即不复用任何view
     * 使用的动画是ObjectAnimator,需要target，且target不是复用的，getView方法所以每次都需要设置
     * 如果AdapterViewFlipper的高度是wrap_content，会自动适配第一个子View
     */
    private fun initAdapterViewFlipper() {
        adapter = object : BaseAdapter() {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val view = convertView ?: View.inflate(context, R.layout.common_simple_text, null)
                view as TextView
                view.text = dataList[position]
                val inAnimator = ObjectAnimator.ofFloat(
                    view,
                    "y",
                    viewBinding.adapterViewFlipper.measuredHeight.toFloat(),
                    0f
                ).apply {
                    duration = 500
                }
                val outAnimator = ObjectAnimator.ofFloat(
                    view,
                    "y",
                    0f,
                    -viewBinding.adapterViewFlipper.measuredHeight.toFloat()
                ).apply {
                    duration = 500
                }
                viewBinding.adapterViewFlipper.inAnimation = inAnimator
                viewBinding.adapterViewFlipper.outAnimation = outAnimator
                view.setOnClickListener {
                    viewBinding.adapterViewFlipper.showNext()
                }
                return view
            }

            override fun getItem(position: Int): Any {
                return dataList[position]
            }

            override fun getItemId(position: Int): Long {
                return position.toLong()
            }

            override fun getCount(): Int {
                return dataList.size
            }
        }
        viewBinding.adapterViewFlipper.adapter = adapter
    }

    /**
     * ViewFlipper 需要一次性加载所有需要添加的view，不会复用任何view
     * 使用的动画是ViewAnimation，可以写在xml里面
     * 如果ViewFlipper的高度是wrap_content，会自动适配最高的那个addView
     */
    private fun initViewFlipper() {
        viewBinding.viewFlipper.removeAllViews()
        repeat(dataList.size) {
            val binding = CommonSimpleTextBinding.inflate(layoutInflater)
            binding.tvName.text = dataList[it]
            viewBinding.viewFlipper.addView(binding.tvName)
        }
    }

}