package com.xxd.thread

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.thread_activity_main.*

@Route(path = "/thread/activity/main")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thread_activity_main)

        val extras = intent.extras
        val string = extras?.getString("key")
        val int = extras?.getInt("position")
        val stringExtra = intent.getStringExtra(ARouter.RAW_URI)
        tvName.text = "thread text"

        tvName.setOnClickListener {
//            val intent = Intent(this,MainActivity::class.java)
//            intent.putExtra("key","法法")
//            intent.putExtra("position",2)
//            startActivity(intent)
        }
            tvName.addOnAttachStateChangeListener(object :View.OnAttachStateChangeListener{
                override fun onViewDetachedFromWindow(v: View?) {
                    Logger.d(this@MainActivity == null)
                    Logger.d(this@MainActivity.isFinishing)
                    Logger.d(this@MainActivity.isDestroyed)
                    Logger.d("onViewDetachedFromWindow")
                }

                override fun onViewAttachedToWindow(v: View?) {
                    Logger.d("onViewAttachedToWindow")
                }

            })

        tvName.text = "11112222"
        Logger.d("设置了文字")




    }
}
