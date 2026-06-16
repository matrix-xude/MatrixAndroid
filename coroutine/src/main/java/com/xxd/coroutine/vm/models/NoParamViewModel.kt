package com.xxd.coroutine.vm.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 *    author : xxd
 *    date   : 2026/5/25
 *    desc   : 无参ViewModel
 */
class NoParamViewModel : ViewModel() {


    fun testCall() {
        println("NoParamViewModel: testCall invoked")
    }

    fun m1(){
        viewModelScope.launch {

        }
    }
}