package com.xxd.view.systemWidget

import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.BaseAdapter
import android.widget.TextView
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.databinding.CommonSimpleTextBinding
import com.xxd.common.extend.binding
import com.xxd.common.extend.onClick
import com.xxd.common.util.log.LogUtil
import com.xxd.view.R
import com.xxd.view.databinding.ViewFragmentFlipperBinding
import com.xxd.view.databinding.ViewFragmentTextLineBinding
import java.util.*

/**
 *    author : xxd
 *    date   : 2020/8/20
 *    desc   : 测试TextView的line的测量规则
 */
class TextViewLineFragment : BaseFragment() {

//    private var viewBinding by binding<ViewFragmentTextLineBinding>()
    private var viewBinding : ViewFragmentTextLineBinding? = null
    private val textArray = listOf(
        "我是一行文字的话",
        "我是一行文字的话,高级的的高度个打发打发发顺丰到付辅导南来北往的人啊五杀",
        "我是一行文字的话,高级的的高度个打发打发发顺丰到付辅导辅导费放到反，无名指放到范德萨发的放到的非保本",
        "我是一行文字的话,高级的的高度个打发打发发顺丰到付辅导辅导费放到反，无名指放到范德萨发的放到的非保本，天下3",
//        "我是一行文字的话,高级的的高度个打发打发发顺丰到付辅导辅导费放到反，无名指放到范德萨发的放到的非保本，天下3多个的更多高大上非常啊反；；放到那边",
        "我是一行文字的话,高级的的高度个打发打发发顺丰\n到付辅导辅导费放到反，无名指放到\n范德萨发的放到的非保本，天下3多个的更多高大上非常啊反；；放到那边",
    )

    private val mNumber = Random().nextInt(100)
    private  var mId : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        LogUtil.d("走了创建View方法")
        viewBinding = ViewFragmentTextLineBinding.inflate(layoutInflater, container, false)
        return viewBinding!!.root
    }

    private val onPreDrawListener = ViewTreeObserver.OnPreDrawListener {
        LogUtil.d(
            "OnPreDraw,当前时间：${System.currentTimeMillis()} $mNumber $mId\n"
//                    "当前的root id, ${viewBinding.root.id}, tv id, ${viewBinding.tv1.id}"
//                    "当前layout的种类，${viewBinding.tv1.layout::class.java.simpleName}\n" +
//                    "当前布局的最大行数：${viewBinding.tv1.layout.lineCount}"
        )
        true
    }

    private val onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        LogUtil.d(
            "OnGlobalLayout,当前时间：${System.currentTimeMillis()}\n"
//                    "当前layout的种类，${viewBinding.tv1.layout::class.java.simpleName}\n" +
//                    "当前布局的最大行数：${viewBinding.tv1.layout.lineCount}"
        )
    }

    override fun onDestroyView() {
//        viewBinding!!.tv1.viewTreeObserver.removeOnPreDrawListener(onPreDrawListener)
//        viewBinding!!.tv1.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)

        super.onDestroyView()
        LogUtil.d("销毁方法走了")

        viewBinding = null
    }

    private var flag = false

    override fun initView() {
        super.initView()

        mId = viewBinding!!.tv1.id

        if (!flag) {
            LogUtil.d("进入了增加监听方法")
            initMeasure()
            flag = true
        }
        initClick()
    }

    private fun initMeasure() {
        viewBinding!!.tv1.movementMethod = LinkMovementMethod()
        viewBinding!!.tv2.movementMethod = LinkMovementMethod()

        viewBinding!!.tv1.layoutChange = {
            viewBinding!!.tv2.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }

//        viewBinding!!.tv1.viewTreeObserver.addOnPreDrawListener(onPreDrawListener)
//        viewBinding!!.tv1.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)


    }

    private fun initClick() {
        viewBinding!!.tvChoose1.onClick {
            viewBinding!!.tv1.text = textArray[0]
        }
        viewBinding!!.tvChoose2.onClick {
            viewBinding!!.tv1.text = textArray[1]
        }
        viewBinding!!.tvChoose3.onClick {
            viewBinding!!.tv1.text = textArray[2]
        }
        viewBinding!!.tvChoose4.onClick {
            viewBinding!!.tv1.text = textArray[3]


        }
    }

}