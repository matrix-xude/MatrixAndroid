package com.xxd.kt.interaction.callback;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * author : xxd
 * date   : 2021/6/25
 * desc   :
 */
public class JavaTest {

    // Java中都可以使用lambda表达式
    private KtCallBack1 cb1 = () -> {
    };
    private KtCallBack2 cb2 = () -> {
    };
    private JavaCallBack cb3 = () -> {
    };

    public interface JavaCallBack {
        void call();
    }

}
