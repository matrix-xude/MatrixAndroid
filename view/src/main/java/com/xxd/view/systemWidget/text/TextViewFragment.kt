package com.xxd.view.systemWidget.text

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.text.CustomClickSpan
import com.xxd.common.extend.binding
import com.xxd.common.extend.onClick
import com.xxd.common.util.log.LogUtil
import com.xxd.common.util.toast.ToastUtil
import com.xxd.view.R
import com.xxd.view.databinding.ViewFragmentTextViewBinding
import java.lang.ref.WeakReference

/**
 *    author : xxd
 *    date   : 2021/9/17
 *    desc   : TextView测量最大占位的行数，字数等
 */
class TextViewFragment() : BaseFragment() {

    private var viewBinding by binding<ViewFragmentTextViewBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ViewFragmentTextViewBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

        viewBinding.tv1.onClick {
            val content = "范德萨范德萨范德萨发都是佛山大佛东范德萨范德萨发大水发多少范德萨发苟富贵 规范 个打发打放到方大搜放到搜房"
            viewBinding.tvContent.text = content
            getLastCharIndexForLimitTextView(viewBinding.tvContent, viewBinding.tvContent.text as String, viewBinding.tvContent.width, 2)
        }

        viewBinding.tv2.onClick {
            imageSpanAlign()
        }

    }

    private fun getLastCharIndexForLimitTextView(textView: TextView, content: String, width: Int, maxLine: Int): Int {
        LogUtil.d("宽度是$width")
        val textPaint = textView.paint
        val staticLayout = StaticLayout(content, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1F, 0F, false)
        val lineCount = staticLayout.lineCount
        LogUtil.d("当前textView的行数=$lineCount")
        LogUtil.d("第0行=${staticLayout.getLineStart(0)}")
        LogUtil.d("第1行=${staticLayout.getLineStart(1)}")
        LogUtil.d("第2行=${staticLayout.getLineStart(2)}")
        LogUtil.d("第3行=${staticLayout.getLineStart(3)}")
        LogUtil.d("第4行=${staticLayout.getLineStart(4)}")
//        LogUtil.d("第5行=${staticLayout.getLineStart(5)}")
        return if (lineCount > maxLine) staticLayout.getLineStart(maxLine) - 1;//exceed
        else -1;//not exceed the max line
    }

    /**
     * 测试文字放入TextView中显示的行数
     * @param textPaint 画笔，一般直接从TextView中获取
     * @param content 需要填充的内容
     * @param width 画笔能使用的宽度，如果从TextView获取宽度，记得去掉padding
     */
    private fun measureMaxLine(textPaint: TextPaint, content: CharSequence, width: Int): Int {
        // Builder为API23,只能使用废弃方法
        val staticLayout = StaticLayout(content, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1F, 0F, false)
        return staticLayout.lineCount
    }

    private fun imageSpanAlign(){
        val ssb = SpannableStringBuilder()
        /*val imageSpan = object : ImageSpan(requireContext(),R.drawable.view_red){

            private var mDrawableRef: WeakReference<Drawable>? = null

            override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
//                super.draw(canvas, text, start, end, x, top, y, bottom, paint)

                val b: Drawable = getCachedDrawable()!!
                canvas.save()

                var transY = bottom - b.bounds.bottom
                if (mVerticalAlignment == ALIGN_BASELINE) {
                    transY -= paint.fontMetricsInt.descent
                } else if (mVerticalAlignment == ALIGN_CENTER) {
                    transY = (bottom - top) / 2 - b.bounds.height() / 2
                }

                canvas.translate(x, transY.toFloat())
                b.draw(canvas)
                canvas.restore()
            }

            private fun getCachedDrawable(): Drawable? {
                val wr: WeakReference<Drawable>? = mDrawableRef
                var d: Drawable? = null
                if (wr != null) {
                    d = wr.get()
                }
                if (d == null) {
                    d = drawable
                    mDrawableRef = WeakReference<Drawable>(d)
                }
                return d
            }
        }*/

        val imageSpan = CustomImageSpan(requireContext(), R.drawable.view_red,CustomImageSpan.TYPE_CENTER)

        ssb.append("1",imageSpan,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssb.append("接上一些文字,多一些，来个换行")
        viewBinding.tvInner.text = ssb
    }



    private fun spanClick() {
//        viewBinding.tvInner.movementMethod = LinkMovementMethod.getInstance()
        viewBinding.tvInner.movementMethod = CustomLinkMovementMethod()
//        viewBinding.root.onClick {
//            ToastUtil.showToast("外部被点击")
//        }
        val clickSpan = object : CustomClickSpan(Color.BLUE) {
            override fun onClick(widget: View) {
                ToastUtil.showToast(requireContext(), "____内部被点击")
                LogUtil.d("我被点击了")
            }
        }

        val ssb = SpannableStringBuilder()
        ssb.append("可以被点击吗",clickSpan,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssb.append("后面的内容啊fdafafaa;fdoifdsf dsf dafdfdsfdsf ")
        viewBinding.tvInner.text = ssb
    }

    private fun spanText() {
        val content = viewBinding.tvContent.text
        val foregroundColorSpan = ForegroundColorSpan(Color.RED)
        val imageSpan = ImageSpan(requireContext(), R.drawable.view_red)
        val clickSpan = object : CustomClickSpan(Color.BLUE) {
            override fun onClick(widget: View) {
                ToastUtil.showToast(requireContext(), "____内部被点击")
                LogUtil.d("我被点击了")

            }
        }

        viewBinding.tvContent.onClick {
            ToastUtil.showToast("外部被点击")
        }


        val spanned = SpannedString(content)

        val spannable = SpannableString(content)
        spannable.setSpan(foregroundColorSpan, 5, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val spannedBuilder = SpannableStringBuilder()
        spannedBuilder.append("天下", foregroundColorSpan, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        spannedBuilder.append("无双")
//        spannedBuilder.setSpan(imageSpan,2,2,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannedBuilder.append("图片", imageSpan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannedBuilder.append("图片22", imageSpan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val spanStart = spannedBuilder.getSpanStart(imageSpan)
        val spanEnd = spannedBuilder.getSpanEnd(imageSpan)
        spannedBuilder.append("$spanStart - $spanEnd")
        spannedBuilder.removeSpan(imageSpan)

        val length = spannedBuilder.length

        spannedBuilder.append("我是可点击的部分", clickSpan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spannedBuilder.setSpan(ForegroundColorSpan(Color.BLACK),length,length+4,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spannedBuilder.removeSpan(clickSpan)

        spannedBuilder.append(" ")

        // 必须加上才有点击事件
        viewBinding.tvContent.movementMethod = LinkMovementMethod.getInstance()
        viewBinding.tvContent.highlightColor = Color.TRANSPARENT


//        spannedBuilder.removeSpan(foregroundColorSpan)


        viewBinding.tvContent.text = spannedBuilder

    }


}