package com.xxd.thread.encode;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;

/**
 *    author : xxd
 *    date   : 2020/9/23
 *    desc   : 自动生成的parcelable
 */
public class ParcelableJava implements Parcelable {

    private int num;
    private String name;
    private int speed;



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.num);
        dest.writeString(this.name);
        dest.writeInt(this.speed);
    }

    public ParcelableJava() {
    }

    protected ParcelableJava(Parcel in) {
        this.num = in.readInt();
        this.name = in.readString();
        this.speed = in.readInt();
    }

    public static final Parcelable.Creator<ParcelableJava> CREATOR = new Parcelable.Creator<ParcelableJava>() {
        @Override
        public ParcelableJava createFromParcel(Parcel source) {
            return new ParcelableJava(source);
        }

        @Override
        public ParcelableJava[] newArray(int size) {
            return new ParcelableJava[size];
        }
    };
}
