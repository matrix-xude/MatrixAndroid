package com.xxd.common;
//import android.graphics.PointF;
import com.xxd.common.service.domain.CommonPoint;

interface CommonFirst{

    String getInfo();

    int add(int a, int b);

    PointF point(int a, int b);

    CommonPoint myPoint(int a, int b);
}