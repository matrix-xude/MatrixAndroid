package com.xxd.common.service.domain

import android.os.Parcel
import android.os.Parcelable

//@Parcelize
class CommonPoint(var x: Int, var y: Int) : Parcelable {

    constructor() : this(0, 0)
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    )

    fun readFromParcel(source: Parcel) {
        x = source.readInt()
        y = source.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(x)
        parcel.writeInt(y)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommonPoint> {
        override fun createFromParcel(parcel: Parcel): CommonPoint {
            return CommonPoint(parcel)
        }

        override fun newArray(size: Int): Array<CommonPoint?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "x:$x , y:$y"
    }

}