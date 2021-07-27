package com.xxd.myself.edit;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

/**
 * author : xxd
 * date   : 2021/7/22
 * desc   :
 */
public class MyJavaEditText extends AppCompatEditText {
    public MyJavaEditText(@NonNull Context context) {
        this(context, null);
    }

    public MyJavaEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyJavaEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
