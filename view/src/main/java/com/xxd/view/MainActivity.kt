package com.xxd.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xxd.apt_annotation.AnnotationTest
import kotlinx.android.synthetic.main.activity_main.*
@AnnotationTest(value = "高大上", code = 4)
class MainActivity : AppCompatActivity() {

    @AnnotationTest(value = "高大上", code = 4)
    private val i = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvName.text = "我是view module"
    }
}
