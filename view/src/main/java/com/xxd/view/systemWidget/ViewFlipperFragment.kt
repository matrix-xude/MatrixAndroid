package com.xxd.view.systemWidget

import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.ViewFlipper
import com.xxd.common.base.BaseFragment
import com.xxd.view.R
import kotlinx.android.synthetic.main.view_fragment_flipper.*

/**
 *    author : xxd
 *    date   : 2020/8/20
 *    desc   : ViewFlipper,AdapterViewFlipper
 */
class ViewFlipperFragment : BaseFragment() {

    private val dataList = mutableListOf<String>()
    private lateinit var adapter: BaseAdapter

    override fun getLayoutId(): Int {
        return R.layout.view_fragment_flipper
    }

    override fun initView() {
        super.initView()
        initAdapterViewFlipper()
    }

    override fun initDataLazy() {
        super.initDataLazy()
        val stringArray = resources.getStringArray(R.array.view_quantum_mechanics)
        dataList.addAll(stringArray)
        adapter.notifyDataSetChanged()
        adapterViewFlipper.startFlipping()
        initViewFlipper()
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
                    adapterViewFlipper.measuredHeight.toFloat(),
                    0f
                ).apply {
                    duration = 500
                }
                val outAnimator = ObjectAnimator.ofFloat(
                    view,
                    "y",
                    0f,
                    -adapterViewFlipper.measuredHeight.toFloat()
                ).apply {
                    duration = 500
                }
                adapterViewFlipper.inAnimation = inAnimator
                adapterViewFlipper.outAnimation = outAnimator
                view.setOnClickListener {
                    adapterViewFlipper.showNext()
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
        adapterViewFlipper.adapter = adapter
    }

    /**
     * ViewFlipper 需要一次性加载所有需要添加的view，不会复用任何view
     * 使用的动画是ViewAnimation，可以写在xml里面
     * 如果ViewFlipper的高度是wrap_content，会自动适配最高的那个addView
     */
    private fun initViewFlipper() {
        viewFlipper.removeAllViews()
        repeat(dataList.size) {
            val tvSimple = View.inflate(context, R.layout.common_simple_text, null) as TextView
            tvSimple.text = dataList[it]
            viewFlipper.addView(tvSimple)
        }
    }

}