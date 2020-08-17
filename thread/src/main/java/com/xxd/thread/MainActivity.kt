package com.xxd.thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.thread_activity_main.*

@Route(path = "/thread/activity/main")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thread_activity_main)

        tvName.text = "thread text"


    }
}
