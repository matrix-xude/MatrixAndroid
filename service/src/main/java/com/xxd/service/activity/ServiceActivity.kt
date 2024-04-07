package com.xxd.service.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.xxd.service.ui.service.ServiceScreen
import com.xxd.service.ui.theme.ServiceTheme


/**
 *    author : xxd
 *    date   : 2024/4/5
 *    desc   :
 */
class ServiceActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ServiceTheme(darkTheme = true) {
                ServiceScreen()
            }
        }


    }
}