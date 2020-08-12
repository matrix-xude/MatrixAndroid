package com.xxd.matrixandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thread_activity_main)
        tvName.text = "app text"

        tvName.setOnClickListener {
//            startActivity(Intent(this,MainActivity::class.java))
            ARouter.getInstance().build("/thread/main").navigation()
        }
    }
}
