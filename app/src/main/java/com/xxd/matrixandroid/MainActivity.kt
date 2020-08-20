package com.xxd.matrixandroid

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.app_activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_main)
        tvName.text = "app text"

        tvName.setOnClickListener {
//            startActivity(Intent(this,MainActivity::class.java))
//            ARouter.getInstance()
//                .build("/thread/activity/main")
//                .withInt("position",1)
//                .withString("key","just do it")
//                .navigation()

            val uri = Uri.Builder()
                .scheme("")
                .authority("mai")
                .path("")
                .appendQueryParameter("","")
                .build()
            ARouter.getInstance()
                .build(Uri.parse("http://main/thread/activity/main?position=1&key=aaa"))
                .navigation()
        }
    }
}
