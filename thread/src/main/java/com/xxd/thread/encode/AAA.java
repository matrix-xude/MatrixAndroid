package com.xxd.thread.encode;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * author : xxd
 * date   : 2023/12/12
 * desc   :
 */
public class AAA implements Parcelable {

    private int a;
    private String bb;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.a);
        dest.writeString(this.bb);
    }

    public void readFromParcel(Parcel source) {
        this.a = source.readInt();
        this.bb = source.readString();
    }

    public AAA() {
    }

    protected AAA(Parcel in) {
        this.a = in.readInt();
        this.bb = in.readString();
    }

    public static final Creator<AAA> CREATOR = new Creator<AAA>() {
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
