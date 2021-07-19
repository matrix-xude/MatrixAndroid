package com.xxd.myself

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *    author : xxd
 *    date   : 2021/7/19
 *    desc   :
 */
class FragmentViewModel : ViewModel() {

    val data1 by lazy {
        MutableLiveData<String>()
    }
}