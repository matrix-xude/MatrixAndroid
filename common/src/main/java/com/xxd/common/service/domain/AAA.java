package com.xxd.common.service.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : xxd
 * date   : 2024/4/17
 * desc   :
 */
public class AAA implements Parcelable {

    private int x;

    public AAA(int x) {
        this.x = x;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.x);
    }

    public void readFromParcel(Parcel source) {
        this.x = source.readInt();
    }

    protected AAA(Parcel in) {
        this.x = in.readInt();
    }

    public static final Parcelable.Creator<AAA> CREATOR = new Parcelable.Creator<AAA>() {
        @Override
        public AAA createFromParcel(Parcel source) {
            return new AAA(source);
        }

        @Override
        public AAA[] newArray(int size) {
            return new AAA[size];
        }
    };
}
