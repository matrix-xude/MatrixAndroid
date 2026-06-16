package com.xxd.coroutine.vm.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.xxd.coroutine.R

/**
 *    author : xxd
 *    date   : 2026/5/25
 *    desc   : 带有 Application 的 ViewModel
 */
class AppViewModel(application: Application) : AndroidViewModel(application) {

    fun testCall() {
        println("AppViewModel: testCall invoked, app: ${getApplication<Application>()} \n appName: ${getAppName()}")
    }

    fun getAppName(): String {
        return getApplication<Application>().getString(R.string.coroutine_app_name)
    }

    inner class MyClose : AutoCloseable {
        override fun close() {
            println("MyClose: close")
        }
    }
}