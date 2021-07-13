package com.xxd.common.util.toast

import android.widget.Toast
import com.xxd.common.base.BaseApplication

/**
 *    author : xxd
 *    date   : 2021/7/13
 *    desc   :
 */
object ToastUtil {

    fun showToast(str: String) {
        Toast.makeText(BaseApplication.application, str, Toast.LENGTH_SHORT).show()
    }
}