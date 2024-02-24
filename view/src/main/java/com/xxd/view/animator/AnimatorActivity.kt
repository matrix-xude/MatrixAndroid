package com.xxd.view.animator

import android.animation.ObjectAnimator
import android.view.ViewGroup
import androidx.core.view.postDelayed
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.logger.Logger
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.extend.onClick
import com.xxd.view.R
import com.xxd.view.databinding.ViewActivityAnimatorBinding

/**
 *    author : xxd
 *    date   : 2024/2/24
 *    desc   : 属性动画
 */
class AnimatorActivity : BaseTitleActivity() {

    private lateinit var viewBinding: ViewActivityAnimatorBinding

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = ViewActivityAnimatorBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence {
        return "属性动画"
    }

    private var translateXStart = 0f
    private var translateXEnd = 50f
    private var rotationStart = 0f
    private var rotationEnd = 90f

    override fun initView() {
        super.initView()

        immersionBar {
            statusBarColor(R.color.common_transparent)
            statusBarDarkFont(true)
            keyboardEnable(true)
        }


        viewBinding.tv2.onClick {
            val objectAnimator = ObjectAnimator.ofFloat(viewBinding.tv1, "translationX", translateXStart, translateXEnd)
            translateXStart += 50
            translateXEnd += 50
            objectAnimator.run {
                duration = 300
                start()
            }
        }

        viewBinding.tv3.onClick {
            viewBinding.tv1.run {
                pivotX = -200f
                pivotY = 200f
            }
            val objectAnimator = ObjectAnimator.ofFloat(viewBinding.tv1, "rotation", rotationStart, rotationEnd)
            rotationStart += 90
            rotationEnd += 90
            objectAnimator.run {
                duration = 300
                start()
            }
        }

        viewBinding.tv4.onClick {
            viewBinding.tv1.run {
                Logger.d("x=$x,y=$y,width=$width,height=$height,left=$left,right=${right},top=${top},bottom=$bottom," +
                        "translationX=$translationX,translationY=$translationY,rotationX=$rotationX,rotationY=$rotationY")
                val intArray = IntArray(2)
                getLocationInWindow(intArray)
                Logger.d("location[0]=${intArray[0]},location[1]=${intArray[1]}")
            }
        }

    }

    fun translateTv1() {
        var startValue = 0f;
        var endValue = 50f;

        viewBinding.tv1.onClick {
            val objectAnimator = ObjectAnimator.ofFloat(viewBinding.tv1, "translationX", startValue, endValue)
            startValue += 50
            endValue += 50
            objectAnimator.run {
                duration = 300
                start()
            }
            viewBinding.tv1.run {
                Logger.d("x=$x,y=$y,width=$width,height=$height,left=$left,right=${right},top=${top},bottom=$bottom,translationX=$translationX,translationY=$translationY")
                val intArray = IntArray(2)
                getLocationInWindow(intArray)
                Logger.d("location[0]=${intArray[0]},location[1]=${intArray[1]}")
            }
        }
    }

    fun rotationTv1() {
        var startValue = 0f;
        var endValue = 90f;

        viewBinding.tv1.onClick {
            viewBinding.tv1.run {
                pivotX = -100f
                pivotY = 100f
            }
            val objectAnimator = ObjectAnimator.ofFloat(viewBinding.tv1, "rotation", startValue, endValue)
            startValue += 90
            endValue += 90
            objectAnimator.run {
                duration = 300
                start()
            }

            viewBinding.tv1.postDelayed(150) {
                viewBinding.tv1.run {
                    Logger.d("x=$x,y=$y,width=$width,height=$height,left=$left,right=${right},top=${top},bottom=$bottom,translationX=$translationX,translationY=$translationY")
                    val intArray = IntArray(2)
                    getLocationInWindow(intArray)
                    Logger.d("location[0]=${intArray[0]},location[1]=${intArray[1]}")
                }
            }
        }
    }

}