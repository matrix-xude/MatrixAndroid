package com.xxd.common.service.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CommonPoint(val x: Int, val y: Int) : Parcelable {

    override fun toString(): String {
        return "x:$x , y:$y"
    }
}