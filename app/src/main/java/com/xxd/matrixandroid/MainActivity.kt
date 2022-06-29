package com.xxd.matrixandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.xxd.common.extend.onClick
import com.xxd.common.provider.IViewProvide
import com.xxd.matrixandroid.databinding.AppActivityMainBinding

class MainActivity : AppCompatActivity() {

    @Autowired
    lateinit var viewProvider: IViewProvide

    private lateinit var viewBinding: AppActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = AppActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)



        initListener()
    }

    private fun initListener() {

        viewBinding.tvName.setOnClickListener {
//            startActivity(Intent(this,MainActivity::class.java))
//            ARouter.getInstance()
//                .build("/view/activity/main")
//                .withInt("position",1)
//                .withString("key","just do it")
//                .navigation()

//            val uri = Uri.Builder()
//                .scheme("")
//                .authority("mai")
//                .path("")
//                .appendQueryParameter("","")
//                .build()
//            ARouter.getInstance()
//                .build(Uri.parse("http://main/thread/activity/main?position=1&key=aaa"))
//                .navigation()
            viewProvider.show("高级")
        }

        viewBinding.tv1.onClick {
            ARouter.getInstance().inject(this)
        }

    }
}
